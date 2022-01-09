from PySide6 import QtWidgets, QtCore


class TextEditWLabel(QtWidgets.QWidget):
    def __init__(self, label):
        super().__init__()

        self.layout = QtWidgets.QHBoxLayout(self)

        self.label = QtWidgets.QLabel(label)
        self.text_box = QtWidgets.QLineEdit()

        self.layout.addWidget(self.label)
        self.layout.addWidget(self.text_box)


class WizardWidget(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()

        # Layouts
        self.layout = QtWidgets.QVBoxLayout(self)
        self.title_layout = QtWidgets.QVBoxLayout(self)
        self.header_layout = QtWidgets.QHBoxLayout(self)

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

        # Mount layouts on top of layouts
        self.layout.addLayout(self.title_layout)
        self.layout.addLayout(self.header_layout)
