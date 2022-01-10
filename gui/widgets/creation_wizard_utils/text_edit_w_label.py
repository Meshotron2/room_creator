from PySide6 import QtWidgets


class TextEditWLabel(QtWidgets.QWidget):
    def __init__(self, label):
        super().__init__()

        self.layout = QtWidgets.QHBoxLayout(self)

        self.label = QtWidgets.QLabel(label)
        self.text_box = QtWidgets.QLineEdit()

        self.layout.addWidget(self.label)
        self.layout.addWidget(self.text_box)