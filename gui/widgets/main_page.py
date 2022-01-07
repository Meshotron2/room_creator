from PySide6 import QtWidgets, QtCore
from PySide6.QtCore import Qt
from PySide6.QtWidgets import QSizePolicy


class MainWidget(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()

        # self.master_layout = QtWidgets.QGridLayout(self)

        # self.layout = QtWidgets.QVBoxLayout(self)
        self.layout = QtWidgets.QGridLayout(self)

        self.title = QtWidgets.QLabel("Meshotron: room creator", alignment=QtCore.Qt.AlignCenter)
        self.btn_select_file = QtWidgets.QPushButton("Select file from disk")
        self.btn_start_wizard = QtWidgets.QPushButton("Start file creator wizard")

        self.layout.addItem(QtWidgets.QSpacerItem(20, 40, QSizePolicy.Minimum, QSizePolicy.Expanding), 2, 1, Qt.AlignTop)
        self.layout.addItem(QtWidgets.QSpacerItem(40, 20, QSizePolicy.Expanding, QSizePolicy.Minimum), 1, 2, Qt.AlignLeft)
        self.layout.addItem(QtWidgets.QSpacerItem(40, 20, QSizePolicy.Expanding, QSizePolicy.Minimum), 3, 2, Qt.AlignRight)
        self.layout.addItem(QtWidgets.QSpacerItem(20, 40, QSizePolicy.Minimum, QSizePolicy.Expanding), 2, 3, Qt.AlignBottom)

        self.content_layout = QtWidgets.QVBoxLayout()
        self.layout.addLayout(self.content_layout, 2, 2)

        self.content_layout.addWidget(self.title)
        self.content_layout.addWidget(self.btn_select_file)
        self.content_layout.addWidget(QtWidgets.QLabel("OR", alignment=QtCore.Qt.AlignCenter))
        self.content_layout.addWidget(self.btn_start_wizard)

        self.btn_select_file.clicked.connect(self.select_file)

    @QtCore.Slot()
    def select_file(self):
        filename, name_filter = QtWidgets.QFileDialog.getOpenFileName(parent=self, caption='Select file to convert',
                                                                      dir='.')
        self.text.setText(filename)
