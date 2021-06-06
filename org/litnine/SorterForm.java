package org.litnine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class SorterForm {
    private JButton sortButton;
    private JTable table;
    private JScrollPane scrollPane;
    public JPanel panel;

    private DefaultTableModel listTableModel;

    public void init() {
        sortButton.addActionListener(e -> {
            FileSorter.sortFiles();
            for (int i=listTableModel.getRowCount()-1;i>=0;i--){
                listTableModel.removeRow(i);
            }
            FileSorter.getCategorizedFiles().clear();
        });

        new DropTarget(panel, new DropTargetListener() {

            @Override
            public void dragEnter(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {

            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragExit(DropTargetEvent dte) {

            }

            @Override
            public void drop(DropTargetDropEvent event) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable transferable = event.getTransferable();
                DataFlavor[] flavors = transferable.getTransferDataFlavors();
                for (DataFlavor flavor : flavors) {
                    try {
                        if (flavor.isFlavorJavaFileListType()) {
                            FileSorter.addFiles((List<File>) transferable.getTransferData(flavor));

                            for (int i=listTableModel.getRowCount()-1;i>=0;i--){
                                listTableModel.removeRow(i);
                            }

                            for(String dirName: FileSorter.getCategorizedFiles().keySet()){
                                for(File file : FileSorter.getCategorizedFiles().get(dirName)){
                                    Path path = file.toPath();
                                    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                                    listTableModel.addRow(new String[]{dirName, path.toString(), attr.creationTime().toString()});
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.dropComplete(true);
            }
        });

        listTableModel = new DefaultTableModel(
                new String[][]{},
                new String[]{
                        "Категория", "Имя", "Время создания"
                });
        table.setModel(listTableModel);
    }
}
