import json
import subprocess

from PySide6 import QtWidgets, QtCore
from PySide6.QtCore import Qt
from PySide6.QtWidgets import QMessageBox, QComboBox
from comunication import tcp_client
from widgets.creation_wizard_utils.element_case_widget import ElementCaseWidget
from widgets.creation_wizard_utils.labeled_widgets import ComboBoxWLabel, TextEditWLabel


class WizardWidget(QtWidgets.QWidget):
    def __init__(self, room: dict = None):
        super().__init__()

        self.use_plugin = room is not None

        # Layouts
        self.layout = QtWidgets.QVBoxLayout(self)
        self.title_layout = QtWidgets.QVBoxLayout(self)
        self.header_layout = QtWidgets.QHBoxLayout(self)
        self.customizer_layout = QtWidgets.QVBoxLayout(self)
        self.btn_layout = QtWidgets.QHBoxLayout(self)

        # title_layout
        self.title = QtWidgets.QLabel("Room creation wizard", alignment=QtCore.Qt.AlignCenter)

        self.title_layout.addWidget(self.title)
        
        values = ["8000","11025","16000","22050","44100","48000"]
        # header_layout
        self.h_x = TextEditWLabel("x(m)", editingDoneCallback=self.roomUpdated)
        self.h_y = TextEditWLabel("y(m)", editingDoneCallback=self.roomUpdated)
        self.h_z = TextEditWLabel("z(m)", editingDoneCallback=self.roomUpdated)
        # self.h_f = TextEditWLabel("f(Hz)", editingDoneCallback=self.roomUpdated)
        self.h_f = ComboBoxWLabel("f(Hz)", values, editingDoneCallback=self.roomUpdated)
        self.h_file = TextEditWLabel("file")
        self.h_left = QtWidgets.QVBoxLayout(self)
        self.h_right = QtWidgets.QVBoxLayout(self)

        self.h_left.addWidget(self.h_x)
        self.h_left.addWidget(self.h_y)
        self.h_left.addWidget(self.h_z)
        self.h_right.addWidget(self.h_f)
        self.h_right.addWidget(self.h_file)

        self.header_layout.addLayout(self.h_left)
        self.header_layout.addLayout(self.h_right)

        # customizer_layout
        # self.scroll_w = QtWidgets.QScrollBar(self)
        # self.customizer_layout.addWidget(self.scroll_w)
        self.scroll = QtWidgets.QScrollArea()
        self.scroll.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOn)
        self.scroll.setHorizontalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        self.scroll.setWidgetResizable(True)

        w = QtWidgets.QWidget()
        w.setLayout(self.customizer_layout)
        self.scroll.setWidget(w)

        self.shapes: list[ElementCaseWidget] = []

        # self.add_shape()

        # btn_layout
        self.add_shape = QtWidgets.QPushButton("Add shape")
        self.create_room = QtWidgets.QPushButton("Create room")

        self.add_shape.clicked.connect(self.add_shape_action)
        self.create_room.clicked.connect(self.send_to_backend)

        self.btn_layout.addWidget(self.add_shape)
        self.btn_layout.addWidget(self.create_room)

        # Mount layouts on top of layouts
        self.layout.addLayout(self.title_layout)
        self.layout.addLayout(self.header_layout)
        # self.layout.addLayout(self.customizer_layout)
        self.layout.addWidget(self.scroll)
        self.layout.addLayout(self.btn_layout)

        if room is not None:
            self.plugin_file = TextEditWLabel("plugin_file")
            self.h_right.addWidget(self.plugin_file)

            self.h_x.set_text(room['xg'])
            self.h_y.set_text(room['yg'])
            self.h_z.set_text(room['zg'])
            self.h_f.set_text(room['f'])
            # self.h_file.set_text(room['file'])
            self.plugin_file.set_text(room['file'])

            for rc in room["shapes"]:
                print(type(room["shapes"][rc]), room["shapes"][rc])
                self.add_widget_slot(room["shapes"][rc])

        # self.room_visualizer = subprocess.Popen(["matlab", "-nodesktop", "-nosplash", "-r", "run room_visualizer.m"], stdin=subprocess.PIPE)

    def roomUpdated(self):
        req_type = "room_plugin" if self.use_plugin else "room_final"

        data = self.fetch_json() if not self.use_plugin else \
            {
                "plugin": self.plugin_file.get_data()[1],
                "room": self.fetch_json()
            }

        to_send = str({"type": req_type, "data": data}).replace("\'", "\"")
        jo = json.loads(to_send)

        f = open("room.txt", "w")
        f.write(json.dumps(jo, indent=4))
        f.close()

    @QtCore.Slot()
    def add_shape_action(self):
        self.add_widget_slot()

    def add_widget_slot(self, data: dict[str, str] = None):
        widget = ElementCaseWidget(str(len(self.shapes)), data, self.roomUpdated)
        self.shapes.append(widget)
        self.customizer_layout.addWidget(widget)
        return widget

   

    @QtCore.Slot()
    def send_to_backend(self):
        req_type = "room_plugin" if self.use_plugin else "room_final"

        data = self.fetch_json() if not self.use_plugin else \
            {
                "plugin": self.plugin_file.get_data()[1],
                "room": self.fetch_json()
            }
        
        if(self.data_is_valid(data)):
            to_send = str({"type": req_type, "data": data}).replace("\'", "\"")
            jo = json.loads(to_send)
            print(json.dumps(jo, indent=4))
            tcp_client.send(str(jo), wait=self.use_plugin)

    def fetch_json(self):
        freq = self.h_f.get_data()[1]
        data = {
            "xg": self.h_x.get_data_conv(freq)[1],
            "yg": self.h_y.get_data_conv(freq)[1],
            "zg": self.h_z.get_data_conv(freq)[1],
            "f": freq,
            "file": self.h_file.get_data()[1],
            "shapes": {
                w.box_id: w.to_json(freq) for w in self.shapes
            }
        }

        if self.use_plugin:
            data["plugin_file"] = self.plugin_file.get_data()[1]

        return data
    
    def data_is_valid(self, data):   # Verificacao erros
        x_room = self.is_float(data['xg'])
        y_room = self.is_float(data['yg'])
        z_room = self.is_float(data['zg'])
        count_S = 0
        count_R = 0
        if x_room!=None and y_room!=None and z_room!=None:
            for i in data['shapes']:           
                s = data['shapes'][i]
                if s['code'] == 'S':        # Contagem dos emissores e recetores
                    count_S += 1
                elif s['code'] == 'R':
                    count_R += 1

                if s['type'] == 'circle':       #Verificacao das coordenadas do objeto
                    x = self.is_float(s['centre_x'])
                    y = self.is_float(s['centre_y'])
                    z = self.is_float(s['centre_z'])
                    r = self.is_float(s['radius'])
                    if x!=None and y!=None and z!=None and r!=None:
                        if x > x_room or y > y_room or z > z_room:
                            self.error_msg("Shape "+str(i)+" coordenates exceed room limits.")
                            return False
                        elif (x + r) > x_room or (x - r) < 0 or (y + r) > y_room or (y - r) < 0 or (z + r) > z_room or (z - r) < 0:
                            self.error_msg("Shape "+str(i)+" radius exceed room limits.")
                            return False
                    else:
                        self.error_msg("Coordenates must be a numeric value!")
                        return False

                elif s['type'] == 'cuboid':
                    x1 = self.is_float(s['x1'])
                    x2 = self.is_float(s['x2'])
                    y1 = self.is_float(s['y1'])
                    y2 = self.is_float(s['y2'])
                    z1 = self.is_float(s['z1'])
                    z2 = self.is_float(s['z2'])
                    if x1!=None and y1!=None and z1!=None and x2!=None and y2!=None and z2!=None:
                        if x1 > x_room or y1 > y_room or z1 > z_room or x2 > x_room or y2 > y_room or z2 > z_room:
                            self.error_msg("Shape "+str(i)+" coordenates exceed room limits.")
                            return False
                    else:
                        self.error_msg("Coordenates must be a numeric value!")
                        return False
                        
        else:
            self.error_msg("Coordenates must be a numeric value!")
            return False

        if count_R == 0:
            self.error_msg("There must be at least one receiver! (code='R')")
            return False
        elif count_S == 0:
            self.error_msg("There must be at least one source! (code='S')")
            return False

        return True

    def is_float(self, elem):
        try:
            elem = float(elem)
            return elem
        except ValueError:
            return None

    def error_msg(self, text):
        msg = QMessageBox()
        msg.setIcon(QMessageBox.Critical)
        msg.setText("Error")
        msg.setInformativeText(text)
        msg.setWindowTitle("Error")
        msg.exec_()

    def closeEvent(self, e):
        self.room_visualizer.kill()
        self.room_visualizer.wait()
        e.accept()

    