from PySide6 import QtWidgets
from PySide6.QtCore import Qt
import math


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

    def get_data_conv(self, freq):
        value = self.text_box.text()
        if value.isnumeric() and freq.isnumeric():
            value = str((int(value) * int(freq)) / (344 * math.sqrt(3)))  # conversao para nós
        return self.replace_label(self.label.text()), value

    def get_data(self):
        return self.replace_label(self.label.text()), self.text_box.text()

    def replace_label(self, text):
        for r in (("(m)", ""), ("(ρ)", "")):
            text = text.replace(*r)
        return text

    def set_text(self, txt: str):
        self.text_box.setText(txt)


class InfoWidget(WidgetWLabel):
    def __init__(self, label: str, info: str):
        self.text_box = QtWidgets.QLineEdit()
        self.text_box.setText(info)

        super().__init__(label, self.text_box)

    def flags(self, _index):
        return Qt.ItemIsEnabled


class ComboBoxWLabel(WidgetWLabel):
    def __init__(self, label: str, items, editingDoneCallback=None):
        self.combo_box = QtWidgets.QComboBox()
        self.combo_box.addItems(items)
        # self.combo_box.setText(value)

        if editingDoneCallback is not None:
            self.combo_box.currentTextChanged.connect(editingDoneCallback)

        super().__init__(label, self.combo_box)

    def get_data(self):
        return self.replace_label(self.label.text()), self.combo_box.currentText()

    def replace_label(self, text):
        for r in (("(m)", ""), ("(ρ)", "")):
            text = text.replace(*r)
        return text
