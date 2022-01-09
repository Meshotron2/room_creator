from PySide6 import QtWidgets, QtCore


class MainWidget(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()

        # self.master_layout = QtWidgets.QGridLayout(self)

        # self.layout = QtWidgets.QVBoxLayout(self)
        self.layout = QtWidgets.QVBoxLayout(self)

        self.layout.addWidget(QtWidgets.QComboBox(self.layout, "penis", "butt"))


    @QtCore.Slot()
    def select_option(self):
        filename, name_filter = QtWidgets.QFileDialog.getOpenFileName(parent=self, caption='Select file to convert',
                                                                      dir='.')
        self.text.setText(filename)
