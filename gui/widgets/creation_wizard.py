from PySide6 import QtWidgets, QtCore


class ElementCaseWidget(QtWidgets.QWidget):
    def __init__(self, box_id):
        super().__init__()

        self.layout = QtWidgets.QVBoxLayout(self)
        self.selection_layout = QtWidgets.QHBoxLayout(self)

        self.layout.addLayout(self.selection_layout)

        self.label = QtWidgets.QLabel(box_id)
        self.text_box = QtWidgets.QComboBox()
        self.text_box.addItems(["test_1", "text_2"])

        self.selection_layout.addWidget(self.label)
        self.selection_layout.addWidget(self.text_box)

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
        self.customizer_layout = QtWidgets.QVBoxLayout(self)

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

        # Mount layouts on top of layouts
        self.layout.addLayout(self.title_layout)
        self.layout.addLayout(self.header_layout)
        self.layout.addLayout(self.customizer_layout)
