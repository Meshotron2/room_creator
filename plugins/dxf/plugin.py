import sys
import ezdxf

try:
    doc = ezdxf.readfile(sys.argv[1])
    msp = doc.modelspace()
    
    lines = msp.query("LINE").groupby("color").get(1, [])

    for l in lines:
        print(l)
except IOError:
    print(f"Not a DXF file or a generic I/O error.")
    sys.exit(1)
except ezdxf.DXFStructureError:
    print(f"Invalid or corrupted DXF file.")
    sys.exit(2)
