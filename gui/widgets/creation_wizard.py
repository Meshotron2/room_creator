from PySide6 import QtWidgets, QtCore

from widgets.creation_wizard_utils.element_case_widget import ElementCaseWidget
from widgets.creation_wizard_utils.text_edit_w_label import TextEditWLabel


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
        self.customizer_layout.addWidget(ElementCaseWidget("1234"))

        # btn_layout
        self.add_shape = QtWidgets.QPushButton("Add shape")
        self.create_room = QtWidgets.QPushButton("Create room")

        self.btn_layout.addWidget(self.add_shape)
        self.btn_layout.addWidget(self.create_room)

        # Mount layouts on top of layouts
        self.layout.addLayout(self.title_layout)
        self.layout.addLayout(self.header_layout)
        self.layout.addLayout(self.customizer_layout)
        self.layout.addLayout(self.btn_layout)
