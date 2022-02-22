from PySide6 import QtWidgets, QtCore
from PySide6.QtWidgets import QSizePolicy

from widgets.creation_wizard import WizardWidget
from widgets.status_page import StatusPageWidget


class MainWidget(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()

        # self.master_layout = QtWidgets.QGridLayout(self)

        # self.layout = QtWidgets.QVBoxLayout(self)
        self.layout = QtWidgets.QGridLayout(self)

        self.title = QtWidgets.QLabel("Meshotron: room creator", alignment=QtCore.Qt.AlignCenter)
        self.btn_select_file = QtWidgets.QPushButton("Select file from disk")
        self.btn_start_wizard = QtWidgets.QPushButton("Start file creator wizard")

        # self.layout.addItem(QtWidgets.QSpacerItem(20, 10, QSizePolicy.Minimum, QSizePolicy.Expanding), 0, 1, 1, 1)
        self.layout.addItem(QtWidgets.QSpacerItem(40, 20, QSizePolicy.Expanding, QSizePolicy.Minimum), 1, 0, 1, 1)
        self.layout.addItem(QtWidgets.QSpacerItem(40, 20, QSizePolicy.Expanding, QSizePolicy.Minimum), 1, 2, 1, 1)
        self.layout.addItem(QtWidgets.QSpacerItem(20, 40, QSizePolicy.Minimum, QSizePolicy.Expanding), 2, 1, 1, 1)

        self.content_layout = QtWidgets.QVBoxLayout()
        self.layout.addLayout(self.content_layout, 1, 1)

        self.content_layout.addItem(QtWidgets.QSpacerItem(20, 10, QSizePolicy.Minimum, QSizePolicy.Expanding))
        self.content_layout.addWidget(self.title)
        self.content_layout.addItem(QtWidgets.QSpacerItem(20, 10, QSizePolicy.Minimum, QSizePolicy.Expanding))

        self.content_layout.addWidget(self.btn_select_file)
        self.content_layout.addItem(QtWidgets.QSpacerItem(20, 10, QSizePolicy.Minimum, QSizePolicy.Expanding))

        self.content_layout.addWidget(QtWidgets.QLabel("OR", alignment=QtCore.Qt.AlignCenter))
        self.content_layout.addItem(QtWidgets.QSpacerItem(20, 10, QSizePolicy.Minimum, QSizePolicy.Expanding))

        self.content_layout.addWidget(self.btn_start_wizard)

        self.monitor_btn = QtWidgets.QPushButton("Check the status of a running process")
        self.server_input = QtWidgets.QLineEdit()
        self.server_input.setPlaceholderText("Server ip and port")

        self.content_layout.addItem(QtWidgets.QSpacerItem(20, 10, QSizePolicy.Minimum, QSizePolicy.Expanding))
        self.content_layout.addWidget(self.server_input)
        self.content_layout.addWidget(self.monitor_btn)

        self.btn_select_file.clicked.connect(self.select_file)
        self.btn_start_wizard.clicked.connect(self.open_wizard)
        self.monitor_btn.clicked.connect(self.open_status_monitor)

    @QtCore.Slot()
    def select_file(self):
        filename, name_filter = QtWidgets.QFileDialog.getOpenFileName(parent=self, caption='Select file to convert',
                                                                      dir='.')
        self.text.setText(filename)

    @QtCore.Slot()
    def open_wizard(self):
        self.open_window = WizardWidget()
        self.open_window.show()

    @QtCore.Slot()
    def open_status_monitor(self):
        server = self.server_input.text()
        self.open_window = StatusPageWidget(server)
        self.open_window.show()