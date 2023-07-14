package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.EdgeModel;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interaction;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<String> getVertici(){
		String sql = "SELECT  Localization "
				+ "FROM classification c "
				+ "GROUP BY Localization";
		List<String> result = new ArrayList<>();
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("Localization"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
		
		
	public List<String> getListaGeniLocalizzazione(String localizzazione){
		String sql = "SELECT  c.GeneID "
				+ "FROM classification c "
				+ "WHERE c.Localization = ? ";
		List<String> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localizzazione);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("GeneID"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public Map<String,Interaction> getInteractions(){
		String sql = "SELECT GeneID1,GeneID2,Type "
				+ "FROM interactions "
				+ "GROUP BY GeneID1,GeneID2";
		Map<String,Interaction> result = new HashMap<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				String key = res.getString("GeneID1")+"-"+res.getString("GeneID2");
				result.put(key, new Interaction(res.getString("GeneID1"), res.getString("GeneID2"),res.getString("Type") ));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	public List<EdgeModel> getArchi(){
		String sql = "SELECT DISTINCT c1.Localization,c2.Localization,COUNT(DISTINCT i.`Type`) AS pesoArco "
				+ "FROM classification  c1, classification c2 ,interactions i "
				+ "WHERE ((c1.GeneID = i.GeneID1 AND c2.GeneID = i.GeneID2)OR (c2.GeneID = i.GeneID1 AND c1.GeneID = i.GeneID2) )AND c1.Localization < c2.Localization  "
				+ "GROUP BY c1.Localization,c2.Localization "
				+ "HAVING pesoArco>0";
		List<EdgeModel> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(new EdgeModel(res.getString("c1.Localization"), res.getString("c2.Localization"), res.getInt("pesoArco")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		
	}
	
}
