package org.litnine;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class SorterForm {
    private JButton sortButton;
    private JTable table;
    private JScrollPane scrollPane;
    public JPanel panel;

    public void init() {
        sortButton.addActionListener(e -> { });

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
                            //label.setText(FileSorter.str());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.dropComplete(true);
            }
        });
    }


}
