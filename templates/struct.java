// do not edit - generated by generate.py

package com.raylib;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.nio.ByteOrder;

/**
 * {{ struct_description }}
 */
public class {{ struct_name }} {

    /**
     * The native FFI MemorySegment that holds the data this object wraps.
     */
    public final MemorySegment memorySegment;

    /**
     * Construct with auto memory allocator and fields initialized to zero.
     */
    public {{ struct_name }}() {
        memorySegment = com.raylib.jextract.{{ struct_name }}.allocate(Arena.ofAuto());
    }

    /**
     * Construct with your owm memory allocator and fields not initialized
     */
    public {{ struct_name }}(SegmentAllocator arena) {
        memorySegment = com.raylib.jextract.{{ struct_name }}.allocate(arena);
    }


    /**
     * Construct by wrapping around an already allocated MemorySegment, perhaps from another object
     */
    public {{ struct_name }}(MemorySegment memorySegment){
        this.memorySegment = memorySegment;
    }

    /**
     * Construct with auto memory allocator and fields initialized as specified {% for field in fields %}
     * @param  {{ field.name }} {{ field.description }} {% endfor %}
     */
    public {{ struct_name }}(
        {% for field in fields %} {{ field.java_type }} {{ field.name }}{{ ", " if not loop.last else "" }}
        {% endfor %}
        ){
        memorySegment = com.raylib.jextract.{{ struct_name }}.allocate(Arena.ofAuto());

        {% for field in fields %}
        {{ field.setter }}({{ field.name }});
        {% endfor %}
    }

    /**
     * Construct with your own memory allocator and fields initialized as specified {% for field in fields %}
     * @param  {{ field.name }} {{ field.description }} {% endfor %}
     */
    public {{ struct_name }}( SegmentAllocator arena,
        {% for field in fields %} {{ field.java_type }} {{ field.name }}{{ ", " if not loop.last else "" }}
        {% endfor %}
        ){
        memorySegment = com.raylib.jextract.{{ struct_name }}.allocate(arena);

        {% for field in fields %}
        {{ field.setter }}({{ field.name }});
        {% endfor %}
    }

    /**
     * Equality is tested on basis of both wrapper objects must refer to same native memory location to be considered equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof {{ struct_name }}))
            return false;
        {{ struct_name }} other = ({{ struct_name }})o;
        return this.memorySegment.equals(other.memorySegment);
    }

    @Override
    public int hashCode() {
        return memorySegment.hashCode();
    }

    public {{ struct_name }} getArrayElement(int index){
        return new {{ struct_name }}(com.raylib.jextract.{{ struct_name }}.asSlice(memorySegment, index));
    }

    /**
     * Allocate an array of {{ struct_name }}
     */
    public static {{ struct_name }} allocateArray(long elementCount, SegmentAllocator allocator) {
        return new {{ struct_name }}(com.raylib.jextract.{{struct_name}}.allocateArray(elementCount, allocator));
    }

    /**
     * Allocate an array of {{ struct_name }}
     */
    public static {{ struct_name }} allocateArray(long elementCount) {
        return new {{ struct_name }}(com.raylib.jextract.{{struct_name}}.allocateArray(elementCount, Arena.ofAuto()));
    }

        {% for field in fields %}
        /**
         * {{ field.description }}
         */
        public {{ field.java_type }} {{ field.getter }}() {
                {% if field.is_a_struct or field.is_a_struct_pointer %}
                return new {{ field.java_type }}(com.raylib.jextract.{{struct_name}}.{{field.name}}(memorySegment));
                {% else %}
                return com.raylib.jextract.{{struct_name}}.{{field.name}}(memorySegment){{field.converter_from_memorysegment}};
                {% endif %}
    }
        /**
         * {{ field.description }}
         */
        public void {{ field.setter }}({{ field.java_type }} value){
                com.raylib.jextract.{{ struct_name }}.{{ field.name }}(memorySegment,{{ field.value_to_c_type }});
    }

        /**
         * {{ field.description }}
         */
        public {{ field.java_type }} {{ field.name }}() {
                {% if field.is_a_struct or field.is_a_struct_pointer %}
                return new {{ field.java_type }}(com.raylib.jextract.{{struct_name}}.{{field.name}}(memorySegment));
                {% else %}
                return com.raylib.jextract.{{struct_name}}.{{field.name}}(memorySegment){{field.converter_from_memorysegment}};
                {% endif %}
                }
        /**
         * {{ field.description }}
         */
        public {{struct_name}} {{ field.name }}({{ field.java_type }} value){
                com.raylib.jextract.{{ struct_name }}.{{ field.name }}(memorySegment,{{ field.value_to_c_type }});
                return this;
                }



        {% endfor %}


}
    