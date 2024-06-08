def scale_room_by_2(input_file, output_file):
    with open(input_file, 'r') as file:
        lines = file.readlines()

    width = int(lines[0].strip())
    height = int(lines[1].strip())
    layout = [line.strip() for line in lines[2:]]

    new_width = width * 2
    new_height = height * 2

    scaled_room = []
    
    for row in layout:
        new_row = ''
        for char in row:
            new_row += char * 2
        scaled_room.append(new_row)
        scaled_room.append(new_row)  # Duplicate the row to scale height

    with open(output_file, 'w') as file:
        file.write(f"{new_width}\n")
        file.write(f"{new_height}\n")
        for line in scaled_room:
            file.write(line + '\n')

# Specify the input and output file names
input_file = 'rooms/normal/room9.txt'
output_file = 'rooms/normal/room9.txt'

# Call the function to scale the room
scale_room_by_2(input_file, output_file)