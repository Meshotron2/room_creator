from PySide6 import QtWidgets
from PySide6.QtCore import Qt


class WidgetWLabel(QtWidgets.QWidget):
    def __init__(self, label: str, widget: QtWidgets.QWidget):
        super().__init__()

        self.layout = QtWidgets.QHBoxLayout(self)

        self.label = QtWidgets.QLabel(label)

        self.layout.addWidget(self.label)
        self.layout.addWidget(widget)


class TextEditWLabel(WidgetWLabel):
    def __init__(self, label: str, value: str = "", editingDoneCallback=None):
        self.text_box = QtWidgets.QLineEdit()
        self.text_box.setText(value)

        if editingDoneCallback is not None:
            self.text_box.editingFinished.connect(editingDoneCallback)

        super().__init__(label, self.text_box)

    def get_data(self):
        return self.label.text(), self.text_box.text()

    def set_text(self, txt: str):
        self.text_box.setText(txt)


class InfoWidget(WidgetWLabel):
    def __init__(self, label: str, info: str):
        self.text_box = QtWidgets.QLineEdit()
        self.text_box.setText(info)

        super().__init__(label, self.text_box)

    def flags(self, _index):
        return Qt.ItemIsEnabled
