import sys

if sys.argv[1] == "lm":
    #     print("List Materials")
    data = {
        "xg": "100",
        "yg": "100",
        "zg": "100",
        "f": "8000",
        "file": "test_plugin_file.dwm",
        "shapes": {
            "0": {
                "centre_x": "10",
                "centre_y": "10",
                "centre_z": "10",
                "radius": "34",
                "coefficient": ""
            }
        }
    }

    print(str(data))

if sys.argv[1] == "dwm":
    print("Port to DWM")

    print('\n'.join(sys.argv))