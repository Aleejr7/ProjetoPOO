package br.com.ada.crud;

import br.com.ada.crud.controller.cidade.CidadeController;
import br.com.ada.crud.controller.cidade.CidadeDAOFactory;
import br.com.ada.crud.controller.cidade.impl.CidadeArmazenamentoController;
import br.com.ada.crud.controller.estado.EstadoController;
import br.com.ada.crud.controller.estado.EstadoDAOFactory;
import br.com.ada.crud.controller.estado.impl.EstadoArmazenamentoController;
import br.com.ada.crud.controller.pais.PaisController;
import br.com.ada.crud.controller.pais.PaisDAOFactory;
import br.com.ada.crud.controller.pais.impl.PaisArmazenamentoController;
import br.com.ada.crud.model.cidade.dao.CidadeDAO;
import br.com.ada.crud.model.estado.dao.EstadoDAO;
import br.com.ada.crud.model.pais.dao.PaisDAO;
import br.com.ada.crud.view.CidadeView;
import br.com.ada.crud.view.EstadoView;
import br.com.ada.crud.view.PaisView;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CidadeDAOFactory CIDADE_DAO_FACTORY = CidadeDAOFactory.getInstance();
    private static final EstadoDAOFactory ESTADO_DAO_FACTORY = EstadoDAOFactory.getInstance();
    private static final PaisDAOFactory PAIS_DAO_FACTORY = PaisDAOFactory.getInstance();

    public static void main(String[] args) {
        menu();
    }

    public static void viewCidades(){
        CidadeDAO cidadeDAO = CIDADE_DAO_FACTORY.create();
        CidadeController controller = new CidadeArmazenamentoController(cidadeDAO);

        CidadeView view = new CidadeView(
                controller,
                scanner
        );
        view.menu();
    }
    public static void viewEstados(){
        EstadoDAO estadoDAO = ESTADO_DAO_FACTORY.create();
        EstadoController controller = new EstadoArmazenamentoController(estadoDAO);

        EstadoView view = new EstadoView(
                controller,
                scanner
        );
        view.menu();
    }

    public static void viewPaíses(){
        PaisDAO paisDAO = PAIS_DAO_FACTORY.create();
        PaisController controller = new PaisArmazenamentoController(paisDAO);

        PaisView view = new PaisView(
                controller,
                scanner
        );
        view.menu();
    }

    public static void menu(){
        System.out.println("Informe uma opção:");
        System.out.println("1 - Manipular Cidades");
        System.out.println("2 - Manipular Estados");
        System.out.println("3 - Manipular Países");
        System.out.println("4 - Sair");

        Integer opcao = scanner.nextInt();

        switch(opcao){
            case 1:
                viewCidades();
                break;
            case 2:
                viewEstados();
                break;
            case 3:
                viewPaíses();
                break;
            case 4:
                System.exit(0);
                break;
            default:

        }
        menu();
    }

}
