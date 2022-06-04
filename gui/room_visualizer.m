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
                s.x1 = str2double(s.x1);
                s.x2 = str2double(s.x2);
                s.y1 = str2double(s.y1);
                s.y2 = str2double(s.y2);
                s.z1 = str2double(s.z1);
                s.z2 = str2double(s.z2);
                
                volume(s.x1+1:s.x2+1, s.y1+1:s.y2+1, s.z1+1:s.z2+1) = f;
            elseif s.type == "circle"
                s.centre_x = str2double(s.centre_x);
                s.centre_y = str2double(s.centre_y);
                s.centre_z = str2double(s.centre_z);
                s.radius = str2double(s.radius);

                for h=1:x
                    for j=1:y
                        for k=1:z
                            if dist(s.centre_x, h, s.centre_y, j, s.centre_z, k) <= s.radius
                                volume(h, j, k) = f;
                            end
                        end
                    end
                end
            end
        end

        vol.setVolume(volume);
    
        fprintf("Updated volume\n");
    catch

    end
    pause(2);
end


function d = dist(x1,x2,y1,y2,z1,z2)
    xDist = (x1-x2)^2;
    yDist = (y1-y2)^2;
    zDist = (z1-z2)^2;

    d = sqrt(xDist + yDist + zDist);
end