package com.smartclinical.util;

import com.smartclinical.model.Usuario;

// classe para controlar o tipo de usuário logado no sistema, como uma sessao
public class Sessao {
    private static TipoUser tipoUser;
    private static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuario){
        usuarioLogado = usuario;
    }

    // pega o tipo de usuario na sessao
    public static TipoUser getTipoUser() {
        return tipoUser;
    }

    // define o tipo de usuario
    public static void setTipoUser(TipoUser tipoUser) {
        Sessao.tipoUser = tipoUser;
    }

}
