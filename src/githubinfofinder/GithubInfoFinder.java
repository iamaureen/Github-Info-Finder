/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package githubinfofinder;

import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Ishrat
 */
public class GithubInfoFinder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        System.out.println("Please provide your username");
        String userName = input.next();
        String url = "https://github.com/"+userName+"?tab=repositories" ; 
        demo demoBj = new demo();
        demoBj.extractInfo(url, userName);
        
        
    }
    
}

class demo{
    void extractInfo(String url, String uname) throws IOException{
        Document doc = Jsoup.connect(url).get(); 
        System.out.println("List of Github repositories: ");
        System.out.println("-------------------------------");
        getRepo(doc, uname);              
    }
    
    void getRepo(Document doc, String uname) throws IOException{
        Elements repoName = doc.select("a[href]");
        for(Element name : repoName){
            //get the value from href attribute
            if(name.attr("itemprop").equals("name codeRepository")){
              System.out.println("Github Repo: " + name.text());
              String repoUrl = "https://github.com/"+uname+"/"+name.text();
              System.out.println("Link: "+repoUrl);
              countStatistics(repoUrl);
              System.out.println("-------------------------------");
             
            }
            
        }
    }
    void countStatistics(String repoUrl) throws IOException{
        Document doc = Jsoup.connect(repoUrl).get();
        Elements commitNo = doc.select("div.stats-switcher-wrapper > ul > li.commits > a > span");
        for (Element cn : commitNo){
            System.out.println("Commit Number of this Repo: " + cn.text());            
        }
        
        Elements starNo = doc.select("a[href].social-count.js-social-count");
       
        for (Element sn : starNo){
            System.out.println("Number of Star in this Repo: " + sn.text());            
        }
                
    }
   
}
