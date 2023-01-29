package br.com.ada.crud.view;

import br.com.ada.crud.controller.pais.PaisController;
import br.com.ada.crud.controller.pais.exception.PaisNaoEncontrado;
import br.com.ada.crud.model.pais.Pais;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PaisView {

    private PaisController controller;
    private Scanner scanner;

    public PaisView(
            PaisController controller,
            Scanner scanner
    ) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void cadastrar() {
        Pais pais = new Pais();

        System.out.println("Informe o nome da Pais:");
        String nome = scanner.nextLine();
        pais.setNome(nome);

        System.out.println("Informe a sigla do Pais:");
        String sigla = scanner.nextLine();
        pais.setSigla(sigla);

        controller.cadastrar(pais);
    }

    public void listar(UUID id) {
        Pais pais = controller.ler(id);
        exibirPais(pais);
    }

    public void atualizar() {
        listar();
        System.out.println("Informe o número do Pais que deseja atualizar:");
        int numeroPais = scanner.nextInt();
        scanner.nextLine();
        Pais pais = controller.listar().get(numeroPais - 1);
        atualizar(pais);
    }

    public void atualizar(Pais pais) {
        exibirPais(pais);

        System.out.println("Informe o novo nome:");
        String nome = scanner.nextLine();
        pais.setNome(nome);

        System.out.println("Informe a sigla do Pais:");
        String sigla = scanner.nextLine();
        pais.setSigla(sigla);

        try {
            controller.atualizar(pais.getId(), pais);
        } catch (PaisNaoEncontrado ex) {
            System.out.println("Pais informado não existe na base. Tente novamente.");
        }
    }

    public void apagar() {
        listar();
        System.out.println("Informe o número do Pais que deseja apagar:");
        int numero = scanner.nextInt();
        scanner.nextLine();
        Pais pais = controller.listar().get(numero - 1);
        apagar(pais.getId());
    }

    public void apagar(UUID id) {
        try {
            Pais pais = controller.excluir(id);
            System.out.println("Estado apagado foi:");
            exibirPais(pais);
        } catch (PaisNaoEncontrado ex) {
            System.out.println("Pais não foi apagado pois não foi localizado. Tente novamente!");
        }
    }

    public void listar() {
        List<Pais> paises = controller.listar();
        for (int index = 0; index < paises.size(); index++) {
            System.out.print("| " + (index + 1) + "      ");
            exibirPais(paises.get(index));
        }
    }

    public void exibirPais(Pais pais) {
        System.out.print("| ");
        System.out.print(pais.getNome());
        System.out.print("    | ");
        System.out.print(pais.getSigla());
        System.out.println("    |");
    }

    public void menu() {
        System.out.println("Infome a opção desejada:");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");
        System.out.println("3 - Atualizar");
        System.out.println("4 - Apagar");
        System.out.println("5 - Sair");
        Integer opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1:
                cadastrar();
                break;
            case 2:
                listar();
                break;
            case 3:
                atualizar();
                break;
            case 4:
                apagar();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("Opção invalida!");
        }
        menu();
    }
}
