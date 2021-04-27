package app;

import java.util.ArrayList;
import java.util.List;

public class Sales {
    private int totalSales;
    private List<SaleItem> saleItems = new ArrayList<>();

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void addSaleItem(String name, int quantity) {
        saleItems.add(new SaleItem(name, quantity));
    }
}
