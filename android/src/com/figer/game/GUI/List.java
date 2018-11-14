package com.figer.game.GUI;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

public class List extends Surface {

    private int size;
    private Array<String> elements;
    private int selectedIndex;

    public List(int x, int y, int w, int size) {
        super(x, y, w, 0);
        this.size = size;
        elements = new Array<String>();
        selectedIndex = -1;
    }

    public List(int x, int y, int w){
        super(x, y, w, 0);
        elements = new Array<String>();
        selectedIndex = -1;
    }

    public List(int size){
        this.size = size;
        elements = new Array<String>();
        selectedIndex = -1;
    }

    @Override
    public void update(Input input) {
        if (touchInside(input)) {
            selectedIndex = (input.getY() - y) / size;
            if (input.isTap()) {
                setSignal(Signal.FIRE);
            }
        } else {
            selectedIndex = -1;
        }
    }

    @Override
    public void drawContent(Renderer renderer) {
        for (int i = 0; i < elements.size; i++) {

            if (i == selectedIndex)
                renderer.drawRectangle(x, y+i* size, w, size, Renderer.BLUE);

            renderer.drawText(elements.get(i), x + 5, y + 5 + i* size, Renderer.WHITE);
            renderer.drawEmptyRectangle(x, y+i* size, w, size, Renderer.WHITE);
        }
    }

    @Override
    public void drawBackground(Renderer renderer) {
        renderer.drawRectangle(x, y, w, h, Renderer.OLIVE);
    }

    public void addElement(String element) {
        for (String s : elements) {
            if (s.equals(element)) return;
        }
        elements.add(element);
        resize();
    }

    public void addCollectionElement(String element) {
        elements.add(element);
        resize();
    }

    public String getSelectedElement() {
        return elements.get(selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    private void resize() {
        h = elements.size * size;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }

    public String getElementByIndex(int i) {
        return elements.get(i);
    }

    public int randomCardPicker(){
        return MathUtils.random(0, elements.size - 1);
    }

    public void clear(){
        elements.clear();
    }
}
