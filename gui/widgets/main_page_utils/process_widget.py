from PySide6 import QtWidgets

from widgets.creation_wizard_utils.labeled_widgets import InfoWidget


class ProcessWidget(QtWidgets.QWidget):
    def __init__(self, proc_dict: dict):
        super().__init__()

        self.layout = QtWidgets.QVBoxLayout(self)

        # Node info
        self.info_layout = QtWidgets.QGridLayout(self)

        # byte nodeId, int pid,
        # float cpu, int ram,
        # float progress
        self.info_layout.addWidget(InfoWidget("id: ", str(proc_dict["node_id"])), 0, 0, 1, 1)
        self.info_layout.addWidget((InfoWidget("pid: ", str(proc_dict["pid"]))), 0, 1, 1, 1)

        self.info_layout.addWidget((InfoWidget("cpu: ", str(proc_dict["cpu"]))), 1, 0, 1, 1)
        self.info_layout.addWidget(InfoWidget("ram: ", str(proc_dict["ram"])), 1, 1, 1, 1)

        self.info_layout.addWidget(InfoWidget("progress: ", str(proc_dict["progress"])), 2, 0, 1, 1)

        self.layout.addLayout(self.info_layout)