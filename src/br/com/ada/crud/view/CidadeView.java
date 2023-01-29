package br.com.ada.crud.view;

import br.com.ada.crud.controller.cidade.CidadeController;
import br.com.ada.crud.controller.cidade.exception.CidadeNaoEncontrada;
import br.com.ada.crud.model.cidade.Cidade;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CidadeView {

    private CidadeController controller;
    private Scanner scanner;

    public CidadeView(
            CidadeController controller,
            Scanner scanner
    ) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void cadastrar() {
        Cidade cidade = new Cidade();

        System.out.println("Informe o nome da Cidade:");
        String nome = scanner.nextLine();
        cidade.setNome(nome);

        System.out.println("Informe a sigla de seu Estado:");
        String siglaEstado = scanner.nextLine();
        cidade.setEstado(siglaEstado);

        controller.cadastrar(cidade);
    }

    public void listar(UUID id) {
        Cidade cidade = controller.ler(id);
        exibirCidade(cidade);
    }

    public void atualizar() {
        listar();
        System.out.println("Informe o número da cidade que deseja atualizar:");
        int numeroCidade = scanner.nextInt();
        scanner.nextLine();
        Cidade cidade = controller.listar().get(numeroCidade - 1);
        atualizar(cidade);
    }

    public void atualizar(Cidade cidade) {
        exibirCidade(cidade);

        System.out.println("Informe o novo nome:");
        String nome = scanner.nextLine();
        cidade.setNome(nome);

        System.out.println("Informe a sigla da cidade:");
        String siglaEstado = scanner.nextLine();
        cidade.setEstado(siglaEstado);

        try {
            controller.atualizar(cidade.getId(), cidade);
        } catch (CidadeNaoEncontrada ex) {
            System.out.println("Cidade informada não existe na base. Tente novamente.");
        }
    }

    public void apagar() {
        listar();
        System.out.println("Informe o número da cidade que deseja apagar:");
        int numero = scanner.nextInt();
        scanner.nextLine();
        Cidade cidade = controller.listar().get(numero - 1);
        apagar(cidade.getId());
    }

    public void apagar(UUID id) {
        try {
            Cidade cidade = controller.excluir(id);
            System.out.println("Cidade apagada foi:");
            exibirCidade(cidade);
        } catch (CidadeNaoEncontrada ex) {
            System.out.println("Cidade não foi agapada pois não foi localizada. Tente novamente!");
        }
    }

    public void listar() {
        List<Cidade> cidades = controller.listar();
        for (int index = 0; index < cidades.size(); index++) {
            System.out.print("| " + (index + 1) + "      ");
            exibirCidade(cidades.get(index));
        }
    }

    public void exibirCidade(Cidade cidade) {
        System.out.print("| ");
        System.out.print(cidade.getNome());
        System.out.print("    | ");
        System.out.print(cidade.getEstado());
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

