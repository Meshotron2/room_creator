from PySide6 import QtWidgets


class WidgetWLabel(QtWidgets.QWidget):
    def __init__(self, label: str, widget: QtWidgets.QWidget):
        super().__init__()

        self.layout = QtWidgets.QHBoxLayout(self)

        self.label = QtWidgets.QLabel(label)

        self.layout.addWidget(self.label)
        self.layout.addWidget(widget)


class TextEditWLabel(WidgetWLabel):
    def __init__(self, label: str):
        self.text_box = QtWidgets.QLineEdit()

        super().__init__(label, self.text_box)

    def get_data(self):
        return self.label.text(), self.text_box.text()
