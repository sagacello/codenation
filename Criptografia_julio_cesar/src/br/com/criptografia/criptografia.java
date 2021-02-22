package br.com.criptografia;

public class criptografia  {
    public String criptografar(String texto) {
        tratarErros(texto);
        return criptografar(texto, 3);
    }

    public String descriptografar(String texto) {
        tratarErros(texto);
        return criptografar(texto, -3);
    }

    private void tratarErros(String texto) {
        if (texto == null) {
            throw new NullPointerException("texto nulo");
        }
        else if (texto.equals("")) {
            throw new IllegalArgumentException("texto vazio");
        }
    }

    public static String criptografar(String texto, int chave) {
        char[] text_chars = texto.toLowerCase().toCharArray();
        String alfabeto = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < text_chars.length; i++) {
            int newIndex;
            int index = alfabeto.indexOf(text_chars[i]);
            if (index > -1) {
                if (index + chave < 0) {
                    newIndex = alfabeto.length() - (index - chave) ;
                    System.out.println(newIndex);

                } else if (index + chave >= alfabeto.length()) {
                    newIndex = (index + chave) - (alfabeto.length());
                } else {
                    newIndex = index + chave;
                }
                text_chars[i] = alfabeto.charAt(newIndex);
            }

        }
        return String.valueOf(text_chars);
    }
}