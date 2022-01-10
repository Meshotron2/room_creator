from enum import Enum

from PySide6 import QtWidgets, QtCore

from widgets.creation_wizard_utils.text_edit_w_label import TextEditWLabel

shapes = {
    "circle": ("centre x", "centre y", "centre z", "radius"),
    "cuboid": ("x1", "y1", "z1", "x2", "y2", "z2")
}


class ElementCaseWidget(QtWidgets.QWidget):
    def __init__(self, box_id):
        super().__init__()

        self.layout = QtWidgets.QVBoxLayout(self)
        self.selection_layout = QtWidgets.QHBoxLayout(self)
        self.customization_layout = QtWidgets.QHBoxLayout(self)

        self.layout.addLayout(self.selection_layout)
        self.layout.addLayout(self.customization_layout)

        self.label = QtWidgets.QLabel(box_id)
        self.combo_box = QtWidgets.QComboBox()
        self.combo_box.addItems([k for k in shapes.keys()])

        self.selection_layout.addWidget(self.label)
        self.selection_layout.addWidget(self.combo_box)

        self.right = QtWidgets.QVBoxLayout(self)
        self.left = QtWidgets.QVBoxLayout(self)
        self.r_widgets = []
        self.l_widgets = []

        self.customization_layout.addLayout(self.right)
        self.customization_layout.addLayout(self.left)

        self.combo_box.currentTextChanged.connect(self.update)

    @QtCore.Slot()
    def update(self):
        for w in self.r_widgets:
            self.right.removeWidget(w)
            del w
        for w in self.l_widgets:
            self.left.removeWidget(w)
            del w
        # self.right = QtWidgets.QVBoxLayout(self)
        # self.left = QtWidgets.QVBoxLayout(self)

        index = self.combo_box.currentText()
        print(index, shapes[index])

        to_add = shapes[index]

        for v in to_add[0:int(len(to_add)/2)]:
            w_label = TextEditWLabel(v)
            self.l_widgets.append(w_label)
            self.left.addWidget(w_label)

        for v in to_add[int(len(to_add)/2):len(to_add)]:
            w_label = TextEditWLabel(v)
            self.r_widgets.append(w_label)
            self.right.addWidget(w_label)
