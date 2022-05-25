volume = zeros(2,2,2, 'uint8');
vol = volshow(volume, BackgroundColor='w', Colormap=jet, Renderer='VolumeRendering', Lighting=false);
oldText = "";

while 1
    try
        text = fileread('room.txt',Encoding='utf-8');
        
        if strcmp(oldText, text)
            pause(2);
            continue;
        end
        
        oldText = text;
        json = jsondecode(text);

        x = str2double(json.data.xg);
        y = str2double(json.data.yg);
        z = str2double(json.data.zg);
        f = str2double(json.data.f);

        shapes = struct2cell(json.data.shapes);

        volume = zeros(x, y, z, 'uint8');

        for i=1:length(shapes)
            s = shapes{i, 1};
            
            s.x1 = str2double(s.x1);
            s.x2 = str2double(s.x2);
            s.y1 = str2double(s.y1);
            s.y2 = str2double(s.y2);
            s.z1 = str2double(s.z1);
            s.z2 = str2double(s.z2);
            f=0;

            switch s.coefficient
                case ' '
                    f = 0;
                case 'R'
                    f = 90;
                case 'S'
                    f = 200;
                otherwise
                    f = 10;
            end

            if s.type == "cuboid"
                volume(s.x1+1:s.x2+1, s.y1+1:s.y2+1, s.z1+1:s.z2+1) = f;
            elseif s.type == "circle"
            
            end
        end

        vol.setVolume(volume);
    
        fprintf("Updated volume\n");
    catch

    end
    pause(2);
end