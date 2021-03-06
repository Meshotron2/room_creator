import json
from enum import Enum

from PySide6 import QtWidgets, QtCore

from widgets.creation_wizard_utils.labeled_widgets import ComboBoxWLabel, TextEditWLabel

shapes = {
    "Select_type": (),
    "circle": ("centre_x(m)", "centre_y(m)", "centre_z(m)", "radius(m)", "coefficient"),
    "cuboid": ("x1(m)", "y1(m)", "z1(m)", "x2(m)", "y2(m)", "z2(m)", "coefficient")
    # "source": ("x(m)", "y(m)", "z(m)"),
    # "receiver": ("x(m)", "y(m)", "z(m)")
}

codes = ["S", "R", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Z"]


class ElementCaseWidget(QtWidgets.QWidget):
    def __init__(self, box_id: str, data: dict[str, str] = None, editingDoneCallback=None):
        super().__init__()

        self.editingDoneCallback = editingDoneCallback

        self.box_id = box_id
        self.ws: list[TextEditWLabel] = []

        self.layout = QtWidgets.QVBoxLayout(self)
        self.selection_layout = QtWidgets.QHBoxLayout(self)
        self.customization_layout = QtWidgets.QHBoxLayout(self)

        self.layout.addLayout(self.selection_layout)
        self.layout.addLayout(self.customization_layout)

        self.label = QtWidgets.QLabel(box_id)
        self.combo_box = QtWidgets.QComboBox()
        if data is None or "type" not in data:
            self.combo_box.addItems([k for k in shapes.keys()])
        else:
            self.combo_box.addItem(data["type"])

        self.selection_layout.addWidget(self.label)
        self.selection_layout.addWidget(self.combo_box)

        self.right = QtWidgets.QVBoxLayout(self)
        self.left = QtWidgets.QVBoxLayout(self)
        # self.r_widgets = []
        # self.l_widgets = []

        self.customization_layout.addLayout(self.left)
        self.customization_layout.addLayout(self.right)

        self.combo_box.currentTextChanged.connect(self.update)

        if data is not None:
            self.populate(data)

    def populate(self, data: dict[str, str]):
        # to_add = [(k, data[k]) for k in data]
        to_add = []
        for k in data:
            if k == "type":
                continue
            to_add.append((k, data[k]))

        mid = int(len(to_add) / 2)

        for k, v in to_add[0:mid]:
            if k == "coefficient":
                w_label = ComboBoxWLabel(k, codes, self.editingDoneCallback)
            else:
                w_label = TextEditWLabel(k, v, self.editingDoneCallback)
            self.ws.append(w_label)
            self.left.addWidget(w_label)

        for k, v in to_add[mid:len(to_add)]:
            if k == "coefficient":
                w_label = ComboBoxWLabel(k, codes, self.editingDoneCallback)
            else:
                w_label = TextEditWLabel(k, v, self.editingDoneCallback)
            self.ws.append(w_label)
            self.right.addWidget(w_label)

    @QtCore.Slot()
    def update(self):

        print(f"CNT: {self.right.count()} {self.left.count()}")
        if self.right.count() != 0:
            while (w := self.right.takeAt(0)) is not None:
                # w.widget().deleteLater()
                w.widget().setParent(None)
        if self.left.count() != 0:
            while (w := self.left.takeAt(0)) is not None:
                # w.widget().deleteLater()
                w.widget().setParent(None)

        # self.right = QtWidgets.QVBoxLayout(self)
        # self.left = QtWidgets.QVBoxLayout(self)

        index = self.combo_box.currentText()
        print(index, shapes[index])

        to_add = shapes[index]
        mid = int(len(to_add) / 2)

        for v in to_add[0:mid]:
            if v == "coefficient":
                w_label = ComboBoxWLabel(v, codes, editingDoneCallback=self.editingDoneCallback)
            else:
                w_label = TextEditWLabel(v, editingDoneCallback=self.editingDoneCallback)
            self.ws.append(w_label)
            self.left.addWidget(w_label)

        for v in to_add[mid:len(to_add)]:
            if v == "coefficient":
                w_label = ComboBoxWLabel(v, codes, editingDoneCallback=self.editingDoneCallback)
            else:
                w_label = TextEditWLabel(v, editingDoneCallback=self.editingDoneCallback)
            self.ws.append(w_label)
            self.right.addWidget(w_label)

    def to_json(self, freq):
        data = {"type": self.combo_box.currentText()}
        for k in self.ws:
            if isinstance(k, ComboBoxWLabel):
                pair = k.get_data()
            else:
                pair = k.get_data_conv(freq)
            data[pair[0]] = pair[1]
        # data = {
        #     "type": self.combo_box.currentText(),
        #     {v.get_data()[0]: v.get_data()[1] for v in self.ws}
        # }

        # import pprint
        # pprint.pprint(str(data))
        return data
