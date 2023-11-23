#version 330 core
layout(location = 0) in vec3 vertexPos;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 uv;

uniform mat4 JEXMVP;
uniform mat4 JEXmodel;
uniform mat4 JEXview;
uniform mat4 JEXprojection;
uniform vec3 JEXpos;


void main() {
    // multiply by new identity matrix
    gl_Position = JEXMVP*vec4(vertexPos, 1.0);

}