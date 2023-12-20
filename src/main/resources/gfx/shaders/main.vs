#version 410 core

layout (location=0) in vec3 position;

out vec4 colour;

void main()
{
    gl_Position = vec4(position, 1.0);
    colour = vec4(1.0, 0.0, 0.0, 1.0);
}