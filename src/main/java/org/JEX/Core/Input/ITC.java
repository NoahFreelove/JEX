package org.JEX.Core.Input;

/*
ITC - Input to Code
Converts Key codes to Names and vice versa
Converts Mouse codes to Names and vice versa
All codes are GLFW based,
 */

import java.util.HashMap;

public class ITC {
    private static HashMap<String,Integer> keyCodes = new HashMap<>();
    private static HashMap<Integer,String> keyNames = new HashMap<>();

    static {
        // Key Codes
        keyCodes.put("SPACE",32);
        keyCodes.put("APOSTROPHE",39);
        keyCodes.put("COMMA",44);
        keyCodes.put("MINUS",45);
        keyCodes.put("PERIOD",46);
        keyCodes.put("SLASH",47);
        keyCodes.put("0",48);
        keyCodes.put("1",49);
        keyCodes.put("2",50);
        keyCodes.put("3",51);
        keyCodes.put("4",52);
        keyCodes.put("5",53);
        keyCodes.put("6",54);
        keyCodes.put("7",55);
        keyCodes.put("8",56);
        keyCodes.put("9",57);
        keyCodes.put("SEMICOLON",59);
        keyCodes.put("EQUAL",61);
        keyCodes.put("A",65);
        keyCodes.put("B",66);
        keyCodes.put("C",67);
        keyCodes.put("D",68);
        keyCodes.put("E",69);
        keyCodes.put("F",70);
        keyCodes.put("G",71);
        keyCodes.put("H",72);
        keyCodes.put("I",73);
        keyCodes.put("J",74);
        keyCodes.put("K",75);
        keyCodes.put("L",76);
        keyCodes.put("M",77);
        keyCodes.put("N",78);
        keyCodes.put("O",79);
        keyCodes.put("P",80);
        keyCodes.put("Q",81);
        keyCodes.put("R",82);
        keyCodes.put("S",83);
        keyCodes.put("T",84);
        keyCodes.put("U",85);
        keyCodes.put("V",86);
        keyCodes.put("W",87);
        keyCodes.put("X",88);
        keyCodes.put("Y",89);
        keyCodes.put("Z",90);
        keyCodes.put("LEFT_BRACKET",91);
        keyCodes.put("BACKSLASH",92);
        keyCodes.put("RIGHT_BRACKET",93);
        keyCodes.put("GRAVE_ACCENT",96);
        keyCodes.put("WORLD_1",161);
        keyCodes.put("WORLD_2",162);
        keyCodes.put("ESCAPE",256);
        keyCodes.put("ENTER",257);
        keyCodes.put("TAB",258);
        keyCodes.put("BACKSPACE",259);
        keyCodes.put("INSERT",260);
        keyCodes.put("DELETE",261);
        keyCodes.put("RIGHT",262);
        keyCodes.put("LEFT",263);
        keyCodes.put("DOWN",264);
        keyCodes.put("UP",265);
        keyCodes.put("PAGE_UP",266);
        keyCodes.put("PAGE_DOWN",267);
        keyCodes.put("HOME",268);
        keyCodes.put("END",269);
        keyCodes.put("CAPS_LOCK",280);
        keyCodes.put("SCROLL_LOCK",281);
        keyCodes.put("NUM_LOCK",282);
        keyCodes.put("PRINT_SCREEN",283);
        keyCodes.put("PAUSE",284);
        keyCodes.put("F1",290);
        keyCodes.put("F2",291);
        keyCodes.put("F3",292);
        keyCodes.put("F4",293);
        keyCodes.put("F5",294);
        keyCodes.put("F6",295);
        keyCodes.put("F7",296);
        keyCodes.put("F8",297);
        keyCodes.put("F9",298);
        keyCodes.put("F10",299);
        keyCodes.put("F11",300);
        keyCodes.put("F12",301);
        keyCodes.put("LEFT_SHIFT",340);
        keyCodes.put("LEFT_CONTROL",341);
        keyCodes.put("LEFT_ALT",342);
        keyCodes.put("LEFT_SUPER",343);
        keyCodes.put("RIGHT_SHIFT",344);
        keyCodes.put("RIGHT_CONTROL",345);
        keyCodes.put("RIGHT_ALT",346);
        keyCodes.put("RIGHT_SUPER",347);
        keyCodes.put("MENU",348);


        // For key names get every key value from the key codes and invert the map
        for(String key : keyCodes.keySet()){
            keyNames.put(keyCodes.get(key),key);
        }
    }

    private static HashMap<String,Integer> mouseCodes = new HashMap<>();
    private static HashMap<Integer,String> mouseNames = new HashMap<>();

    static {
        // Mouse Codes
        mouseCodes.put("MOUSE_BUTTON_1",0);
        mouseCodes.put("MOUSE_BUTTON_2",1);
        mouseCodes.put("MOUSE_BUTTON_3",2);
        mouseCodes.put("MOUSE_BUTTON_4",3);
        mouseCodes.put("MOUSE_BUTTON_5",4);
        mouseCodes.put("MOUSE_BUTTON_6",5);
        mouseCodes.put("MOUSE_BUTTON_7",6);
        mouseCodes.put("MOUSE_BUTTON_8",7);
        mouseCodes.put("MOUSE_BUTTON_LAST",7);
        mouseCodes.put("LEFT",0);
        mouseCodes.put("RIGHT",1);
        mouseCodes.put("MIDDLE",2);

        // For mouse names get every mouse value from the mouse codes and invert the map
        for(String key : mouseCodes.keySet()){
            mouseNames.put(mouseCodes.get(key),key);
        }
    }

    public static String keyName(int code){
        return keyNames.get(code);
    }
    public static int keyCode(String name){
        return keyCodes.get(name);
    }

    public static String mouseName(int code){
        return mouseNames.get(code);
    }
    public static int mouseCode(String name){
        return mouseCodes.get(name);
    }
}
