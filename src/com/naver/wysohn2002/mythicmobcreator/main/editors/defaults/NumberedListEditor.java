/*******************************************************************************
 *     Copyright (C) 2017 wysohn
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.naver.wysohn2002.mythicmobcreator.main.editors.defaults;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class NumberedListEditor extends EmptyListEditor {
    // this list is not numbered, but super list is numbered.
    protected final List<String> list;

    public NumberedListEditor(List<String> targetList) {
        super(targetList);

        list = new CopyingList(targetList);
    }

    @Override
    protected void onRemove() {
        int[] indices = jList.getSelectedIndices();
        int result = JOptionPane.showConfirmDialog(this, "Are you sure to remove [" + indices.length + "] items?");
        if (result == JOptionPane.OK_OPTION) {
            for (int i = indices.length - 1; i >= 0; i--) {
                list.remove(indices[i]);
            }

            this.updateJList();
        }
    }

    @Override
    protected void onUp() {
        if (list.size() < 2)
            return;

        if (this.getSelectedIndex() <= 0)
            return;

        swap(list, this.getSelectedIndex(), this.getSelectedIndex() - 1);

        updateJList();
    }

    @Override
    protected void onDown() {
        if (list.size() < 2)
            return;

        if (this.getSelectedIndex() > list.size() - 1)
            return;

        swap(list, this.getSelectedIndex(), this.getSelectedIndex() + 1);

        updateJList();
    }

    /**
     * only use toArray(T[]), set(int, String), add(String), add(int, String),
     * remove(int), get(int). Other methods are undefined.
     *
     * @author wysohn
     *
     */
    @SuppressWarnings("serial")
    private class CopyingList extends ArrayList<String> {
        private final List<String> numbered;

        public CopyingList(List<String> target) {
            this.numbered = target;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            for (int i = 0; i < numbered.size(); i++) {
                String value = numbered.get(i);
                a[i] = (T) value.split(" ", 2)[1];
            }
            return a;
        }

        @Override
        public String set(int index, String element) {
            return numbered.set(index, index + " " + element);
        }

        @Override
        public boolean add(String e) {
            return numbered.add(numbered.size() + " " + e);
        }

        @Override
        public void add(int index, String element) {
            numbered.add(index, index + " " + element);
        }

        @Override
        public String remove(int index) {
            return numbered.remove(index);
        }

        @Override
        public String get(int index) {
            return numbered.get(index).split(" ", 2)[1];
        }

        @Override
        public int size() {
            return numbered.size();
        }

        @Override
        public String toString(){
            return numbered.toString();
        }
    }

    @Override
    protected void updateJList() {
        String[] strs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strs[i] = i + " " + list.get(i);
        }
        jList.setListData(strs);
    }
}
