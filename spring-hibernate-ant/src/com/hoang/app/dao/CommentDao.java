package com.hoang.app.dao;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Hoang Truong
 */

@Repository
public class CommentDao {
	
//	private static final String KEY_SPACE		= "BlogApp";
//	private static final String COLUMN_FAMILY	= "COMMENT";
//	private static final String COL_COMMENTER 	= "COMMENTER";
//	private static final String COL_TEXT 		= "TEXT";
//	private static final String COL_DATE 		= "DATE";
//	private ColumnPath commentCP = new ColumnPath(COLUMN_FAMILY);
//	
//	@Autowired
//	private CassandraClientPool pool;
//	
//	public CommentDao() {		
//	}
//	
//	public List<Comment> findAll(String key, String cf) {
//		List<Comment> comments = new ArrayList<Comment>();
//		
//		CassandraClient client = null; 
//		try {
//			client = pool.borrowClient();
//		 	Keyspace keyspace = client.getKeyspace(KEY_SPACE);
//		 	
//			System.out.println("Find all comments of Column Family: '" + cf + "' - Key: '" + key + "'");
//			SliceRange sliceRange = new SliceRange();
//			sliceRange.setStart(StringUtils.bytes(""));
//			sliceRange.setFinish(StringUtils.bytes(""));
//			SlicePredicate predicate = new SlicePredicate();
//			predicate.setSlice_range(sliceRange);			
//			List<SuperColumn> sColumns = keyspace.getSuperSlice(key, new ColumnParent(cf), predicate);			
//			for (SuperColumn sc : sColumns)  {
//				Comment comment = new Comment();
//				comment.setKey(key);
//				comment.setSc(toUUID(sc.name));							    
//			    for (Column column : sc.columns) {
//			    	if (COL_COMMENTER.equals(StringUtils.string(column.name))) {
//			    		comment.setCommenter(StringUtils.string(column.value));
//			    	}
//			    	else if (COL_DATE.equals(StringUtils.string(column.name))) {
//			    		comment.setDate(new Date());			    		
//			    	}
//			    	else if (COL_TEXT.equals(StringUtils.string(column.name))) {
//			    		comment.setText((StringUtils.string(column.value)));			    		
//			    	}			    	
//				}
//			    comments.add(comment);
//			}			
//		}
//		catch (TTransportException ce) {
//			System.err.println("Can not connect to Cassandra server");
//			return comments;
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		return comments;
//	}
//    
//	public void save(Comment comment) {
//		CassandraClient client = null; 
//		try {
//			client = pool.borrowClient();
//		 	Keyspace keyspace = client.getKeyspace(KEY_SPACE);
//			
//			comment.setSc(java.util.UUID.fromString(new UUID().toString()));				
//			commentCP.setSuper_column(asByteArray(comment.getSc()));
//			
//			List<byte []> cols = new ArrayList<byte[]>();
//			cols.add(StringUtils.bytes(COL_COMMENTER));
//			cols.add(StringUtils.bytes(COL_TEXT));
//			cols.add(StringUtils.bytes(COL_DATE));
//			
//			List<byte []> vals = new ArrayList<byte[]>();
//			vals.add(StringUtils.bytes(comment.getCommenter()));
//			vals.add(StringUtils.bytes(comment.getText()));
//			vals.add(StringUtils.bytes(comment.getDate().toString()));
//			
//			for (int i = 0; i < cols.size(); i++) {
//				commentCP.setColumn(cols.get(i));		    
//			    keyspace.insert(comment.getKey(), commentCP, vals.get(i));
//			}
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		finally {
//			// return client to pool; do it in a finally block to make sure it's executed
//			try {
//				pool.releaseClient(client);
//			} 
//			catch (Exception e) {}							
//		}
//	}
//	
//	public Comment read(String key, String cf) {
//		Comment comment = new Comment();
//		
//		CassandraClient client = null; 
//		try {
//			client = pool.borrowClient();
//		 	Keyspace keyspace = client.getKeyspace(KEY_SPACE);
//		    
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}		
//		
//		return comment;
//	}
//	
//	public void delete(Comment comment) {
//		CassandraClient client = null; 
//		try {
//			client = pool.borrowClient();
//		 	Keyspace keyspace = client.getKeyspace(KEY_SPACE);
//		    commentCP.setSuper_column(asByteArray(comment.getSc()));
//			keyspace.remove(comment.getKey(), commentCP);
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}		
//	}
//	
//	public byte[] asByteArray(java.util.UUID uuid)    {
//        long msb = uuid.getMostSignificantBits();
//        long lsb = uuid.getLeastSignificantBits();
//        byte[] buffer = new byte[16];
//
//        for (int i = 0; i < 8; i++) {
//                buffer[i] = (byte) (msb >>> 8 * (7 - i));
//        }
//        for (int i = 8; i < 16; i++) {
//                buffer[i] = (byte) (lsb >>> 8 * (7 - i));
//        }
//
//        return buffer;
//    }
//	
//	public java.util.UUID toUUID( byte[] uuid )    {
//	    long msb = 0;
//	    long lsb = 0;
//	    assert uuid.length == 16;
//	    
//	    for (int i=0; i<8; i++) {
//	        msb = (msb << 8) | (uuid[i] & 0xff);
//	    }
//	    
//	    for (int i=8; i<16; i++) {
//	        lsb = (lsb << 8) | (uuid[i] & 0xff);
//	    }
//	
//	    UUID u = new UUID(msb,lsb);
//	    
//	    return java.util.UUID.fromString(u.toString());
//    }
}
