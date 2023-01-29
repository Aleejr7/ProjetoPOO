package br.com.ada.crud.view;

import br.com.ada.crud.controller.estado.EstadoController;
import br.com.ada.crud.controller.estado.exception.EstadoNaoEncontrado;
import br.com.ada.crud.model.estado.Estado;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class EstadoView {
    private EstadoController controller;
    private Scanner scanner;

    public EstadoView(
            EstadoController controller,
            Scanner scanner
    ) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void cadastrar() {
        Estado estado = new Estado();

        System.out.println("Informe o nome da Estado:");
        String nome = scanner.nextLine();
        estado.setNome(nome);

        System.out.println("Informe a sigla do Estado:");
        String siglaEstado = scanner.nextLine();
        estado.setSigla(siglaEstado);

        System.out.println("Informe a sigla de seu País:");
        String siglaPais = scanner.nextLine();
        estado.setPais(siglaPais);

        controller.cadastrar(estado);
    }

    public void listar(UUID id) {
        Estado estado = controller.ler(id);
        exibirEstado(estado);
    }

    public void atualizar() {
        listar();
        System.out.println("Informe o número do Estado que deseja atualizar:");
        int numeroEstado = scanner.nextInt();
        scanner.nextLine();
        Estado estado = controller.listar().get(numeroEstado - 1);
        atualizar(estado);
    }

    public void atualizar(Estado estado) {
        exibirEstado(estado);

        System.out.println("Informe o novo nome:");
        String nome = scanner.nextLine();
        estado.setNome(nome);

        System.out.println("Informe a sigla do Estado:");
        String siglaEstado = scanner.nextLine();
        estado.setSigla(siglaEstado);

        System.out.println("Informe a sigla do seu País:");
        String siglaPais = scanner.nextLine();
        estado.setPais(siglaPais);

        try {
            controller.atualizar(estado.getId(), estado);
        } catch (EstadoNaoEncontrado ex) {
            System.out.println("Estado informado não existe na base. Tente novamente.");
        }
    }

    public void apagar() {
        listar();
        System.out.println("Informe o número do Estado que deseja apagar:");
        int numero = scanner.nextInt();
        scanner.nextLine();
        Estado estado = controller.listar().get(numero - 1);
        apagar(estado.getId());
    }

    public void apagar(UUID id) {
        try {
            Estado estado = controller.excluir(id);
            System.out.println("Estado apagado foi:");
            exibirEstado(estado);
        } catch (EstadoNaoEncontrado ex) {
            System.out.println("Estado não foi apagado pois não foi localizado. Tente novamente!");
        }
    }

    public void listar() {
        List<Estado> estados = controller.listar();
        for (int index = 0; index < estados.size(); index++) {
            System.out.print("| " + (index + 1) + "      ");
            exibirEstado(estados.get(index));
        }
    }

    public void exibirEstado(Estado estado) {
        System.out.print("| ");
        System.out.print(estado.getNome());
        System.out.print("    | ");
        System.out.print(estado.getSigla());
        System.out.print("    |");
        System.out.print(estado.getPais());
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
