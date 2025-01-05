package com.smartclinical.util;

// classe para controlar o tipo de usuário logado no sistema, como uma sessao
public class Sessao {
    private static TipoUser tipoUser;

    // pega o tipo de usuario na sessao
    public static TipoUser getTipoUser() {
        return tipoUser;
    }

    // define o tipo de usuario
    public static void setTipoUser(TipoUser tipoUser) {
        Sessao.tipoUser = tipoUser;
    }

}
