#version 410 core

layout (location=0) in vec2 position;
layout (location=1) in vec2 texture_base_coordinates;
layout (location=2) in float texture_index;

out vec2 texture_coordinates;

vec2 texture_coordinates_from_index(vec2 base, float i)
{

    float row = 1.0f - ((1.0f/128.0f) * floor(i / 128.0f));
    float column = (1.0f / 128.0f) * float(int(i) % 128);

    return vec2(column + (base.x * (1.0f / 128.0f)), row - (base.y * (1.0f / 128.0f)));
}

void main()
{
    gl_Position = vec4(position, 0.0, 1.0);
    texture_coordinates = texture_coordinates_from_index(texture_base_coordinates, texture_index);
}