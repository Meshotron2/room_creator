import json

from PySide6 import QtWidgets, QtCore
from PySide6.QtCore import Qt

from comunication import tcp_client
from widgets.creation_wizard_utils.element_case_widget import ElementCaseWidget
from widgets.creation_wizard_utils.labeled_widgets import TextEditWLabel


class WizardWidget(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()

        # Layouts
        self.layout = QtWidgets.QVBoxLayout(self)
        self.title_layout = QtWidgets.QVBoxLayout(self)
        self.header_layout = QtWidgets.QHBoxLayout(self)
        self.customizer_layout = QtWidgets.QVBoxLayout(self)
        self.btn_layout = QtWidgets.QHBoxLayout(self)

        # title_layout
        self.title = QtWidgets.QLabel("Room creation wizard", alignment=QtCore.Qt.AlignCenter)

        self.title_layout.addWidget(self.title)

        # header_layout
        self.h_x = TextEditWLabel("x")
        self.h_y = TextEditWLabel("y")
        self.h_z = TextEditWLabel("z")
        self.h_f = TextEditWLabel("f")
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

    @QtCore.Slot()
    def add_shape_action(self):
        widget = ElementCaseWidget(str(len(self.shapes)))
        self.shapes.append(widget)
        self.customizer_layout.addWidget(widget)

    @QtCore.Slot()
    def send_to_backend(self):
        to_send = str({"type": "room_final", "data": self.fetch_json()}).replace("\'", "\"")
        jo = json.loads(to_send)
        print(json.dumps(jo, indent=4))
        tcp_client.send(str(jo))

    def fetch_json(self):
        data = {
            "xg": self.h_x.get_data()[1],
            "yg": self.h_y.get_data()[1],
            "zg": self.h_z.get_data()[1],
            "f": self.h_f.get_data()[1],
            "file": self.h_file.get_data()[1],
            "shapes": {
                w.box_id: w.to_json() for w in self.shapes
            }
        }
        return data
