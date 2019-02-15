package com.zmysna.solr;

import com.zmysna.solr.entity.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class App_Solr_CRUD {

    private SolrServer solrServer = new HttpSolrServer("http://localhost:8082/solr/collection1");

    @Test
    public void saveOrUpdate() throws IOException, SolrServerException {

        Product product = new Product();
        product.setPid("9527");
        product.setName("厄尼透佛");
        product.setCatalogName("图书");
        product.setPrice(21.4D);
        product.setDescription("这本书真的好");
        product.setPicture("4.jpg");
        solrServer.addBean(product);
        solrServer.commit();
    }

    @Test
    public void delete() throws IOException, SolrServerException {
        solrServer.deleteById("9527");
        solrServer.commit();
    }

    @Test
    public void deleteByQuery() throws IOException, SolrServerException {
        solrServer.deleteByQuery("pid:5");
        solrServer.commit();
    }

    @Test
    public void query() throws SolrServerException {
        SolrQuery query= new SolrQuery("*.*");
        query.setStart(59);
        query.setRows(5);
        QueryResponse response = solrServer.query(query);
        List<Product> products = response.getBeans(Product.class);
        System.out.println("命中个数:"+ products.size());
        for (Product product : products) {
            System.out.println("===========================================================================================");
            System.out.println(product.getPid());
            System.out.println(product.getName());
            System.out.println(product.getCatalogName());
            System.out.println(product.getPrice());
            System.out.println(product.getDescription());
            System.out.println(product.getPicture());
        }
    }

}
