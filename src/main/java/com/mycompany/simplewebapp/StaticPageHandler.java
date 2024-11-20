package com.mycompany.simplewebapp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StaticPageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("exchange: " + exchange);
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            String html = """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <title>Inventory Management</title>
                            <style>
                                body { font-family: Arial, sans-serif; margin: 20px; }
                                table { width: 100%; border-collapse: collapse; }
                                th, td { padding: 10px; text-align: left; border: 1px solid #ddd; }
                                h1 { text-align: center; }
                                .actions { display: flex; justify-content: space-around; margin-bottom: 20px; }
                                .section { width: 30%; }
                            </style>
                            <script>
                                async function fetchItems() {
                                    const response = await fetch('/items');
                                    const items = await response.json();
                                    const stockArea = document.getElementById('stockArea');
                                    stockArea.innerHTML = '<table><tr><th>Item</th><th>Quantity</th></tr>' +
                                        Object.entries(items).map(([name, qty]) =>
                                            `<tr><td>${name}</td><td>${qty}</td></tr>`).join('') + '</table>';
                                }

                                async function addItem() {
                                    const newItem = document.getElementById('newItem').value;
                                    const qty = document.getElementById('newQty').value || 1;
                                    await fetch('/items', {
                                        method: 'POST',
                                        body: `${newItem},${qty}`
                                    });
                                    document.getElementById('newItem').value = '';
                                    document.getElementById('newQty').value = '';
                                    fetchItems();
                                }

                                async function removeItem() {
                                    const removeItem = document.getElementById('removeItem').value;
                                    await fetch(`/items?name=${removeItem}`, { method: 'DELETE' });
                                    document.getElementById('removeItem').value = '';
                                    fetchItems();
                                }

                                async function recordSale() {
                                    const saleItem = document.getElementById('saleItem').value;
                                    const saleQty = document.getElementById('saleQty').value || 1;
                                    await fetch('/items', {
                                        method: 'POST',
                                        body: `${saleItem},-${saleQty}`
                                    });
                                    document.getElementById('saleItem').value = '';
                                    document.getElementById('saleQty').value = '';
                                    fetchItems();
                                }
                            </script>
                        </head>
                        <body onload="fetchItems()">
                            <h1>Inventory Management</h1>
                            <div class="actions">
                                <div class="section">
                                    <h3>Add Item</h3>
                                    <input type="text" id="newItem" placeholder="Enter new item" />
                                    <input type="number" id="newQty" placeholder="Quantity" min="1" />
                                    <button onclick="addItem()">Add</button>
                                </div>
                                <div class="section">
                                    <h3>Remove Item</h3>
                                    <input type="text" id="removeItem" placeholder="Enter item to remove" />
                                    <button onclick="removeItem()">Remove</button>
                                </div>
                                <div class="section">
                                    <h3>Record Sale</h3>
                                    <input type="text" id="saleItem" placeholder="Enter item sold" />
                                    <input type="number" id="saleQty" placeholder="Quantity" min="1" />
                                    <button onclick="recordSale()">Record Sale</button>
                                </div>
                            </div>
                            <div id="stockArea">
                                <!-- Stock area will be populated by fetchItems() -->
                            </div>
                        </body>
                        </html>
                        """;

            exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(html.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}
