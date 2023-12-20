#version 410 core

out vec4 pixel;

in vec2 texture_coordinates;

uniform sampler2D image;

void main()
{
    pixel = texture(image, texture_coordinates);
} 