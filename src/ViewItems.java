/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

//класс просмотра имеющихся книг и двд
public class ViewItems extends JFrame {
    private Object[][] array = new String[150][7];
    private Object[] columnsHeader;

    public ViewItems( UniLibraryManager uniLibraryManager) {
        JFrame frame = new JFrame("Items list");
        columnsHeader = new String[]{"Type item", "ISBN", "Title", "Sector", "Publication date", "Borrow Time", "Reader"};
        for (int i = 0; i < uniLibraryManager.listItem.size(); i++) {
            array[i][0] = uniLibraryManager.listItem.get(i).getClass().getName();
            array[i][1] = uniLibraryManager.listItem.get(i).getISBN();
            array[i][2] = uniLibraryManager.listItem.get(i).getTitle();
            array[i][3] = uniLibraryManager.listItem.get(i).getSector();
            if (uniLibraryManager.listItem.get(i).getPublicationDate() != null) {
                array[i][4] = uniLibraryManager.listItem.get(i).getPublicationDate().toString();
            } else
                array[i][4] = "";
            if (uniLibraryManager.listItem.get(i).getBorrowTime() != null) {
                array[i][5] = uniLibraryManager.listItem.get(i).getBorrowTime().toString();
            } else
                array[i][5] = "";
            if (uniLibraryManager.listItem.get(i).getCurrentReader() != null) {
                array[i][6] = uniLibraryManager.listItem.get(i).getCurrentReader().getName();
            } else
                array[i][6] = "";
        }

        TableModel model = new DefaultTableModel(array, columnsHeader){
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }

        };

        final JTable table = new JTable(model);
//        table.setDefaultRenderer(Object.class, new TableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table,
//                                                           Object value, boolean isSelected, boolean hasFocus,
//                                                           int row, int column) {
//                JPanel pane = new JPanel();
//                pane.setBackground(Color.GREEN);
//                return null;
//            }
//        });


        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Search by Title: ");
        panel.add(label, BorderLayout.WEST);
        final JTextField filterText = new JTextField("");
        panel.add(filterText, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);
        JButton button = new JButton("Search");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = filterText.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 900;
        int height = 400;

        frame.add(button, BorderLayout.SOUTH);
        frame.setBounds((screenSize.width / 2 - width / 2), (screenSize.height / 2 - height / 2), width, height);
        frame.setVisible(true);
    }

    public static void main(String[] args, UniLibraryManager uniLibraryManager) {
        new ViewItems(uniLibraryManager);
    }
}
