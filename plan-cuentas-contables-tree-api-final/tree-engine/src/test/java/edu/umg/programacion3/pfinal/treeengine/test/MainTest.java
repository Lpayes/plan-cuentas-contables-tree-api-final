package edu.umg.programacion3.pfinal.treeengine.test;

import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeStrategy;

public class MainTest {

    public static void main(String[] args) {

        CustomTreeStrategy tree =
                new CustomTreeStrategy();

        tree.createRoot("Activo");

        tree.addChild(1L, "Caja");
        tree.addChild(1L, "Bancos");

    }
}