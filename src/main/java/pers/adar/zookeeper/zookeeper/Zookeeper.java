/**
 * Copyright (c) 2015, adar.w (adar-w@outlook.com) 
 * 
 * http://www.adar-w.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pers.adar.zookeeper.zookeeper;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class Zookeeper {
	
	private static final String HOST = "172.16.36.150";
	
	private static final String CONNECT;
	
	static {
		CONNECT = String.format("%s:2181,%s:2182,%s:2183", HOST, HOST, HOST);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getClient().getState());
	}
	
	public static ZooKeeper getClient() throws Exception {
		return getClient(null, null);
	}
	
	public static ZooKeeper getClient(String scheme, byte[] auth) throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		
		ZooKeeper zooKeeper = new ZooKeeper(CONNECT, 5000, (e) -> {
			latch.countDown();
		});
			
		latch.await();
		
		if (StringUtils.isNotEmpty(scheme) && auth != null) {
			zooKeeper.addAuthInfo(scheme, auth);
		}
		
		return zooKeeper;
	}
	
	public static void auth(ZooKeeper zooKeeper) throws NoSuchAlgorithmException, KeeperException, InterruptedException {
		ACL acl = new ACL(Perms.READ, new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
		
		zooKeeper.create("/auth", "auth".getBytes(), Collections.singletonList(acl), CreateMode.EPHEMERAL);
	}
	
	public static void authForRoot(ZooKeeper zooKeeper) throws NoSuchAlgorithmException, KeeperException, InterruptedException {
		ACL acl = new ACL(Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
		
		zooKeeper.setACL("/", Collections.singletonList(acl), -1);
	}
	
	public static void createNodeWithAuth(ZooKeeper zooKeeper, String path, String username, String password) throws KeeperException, InterruptedException, NoSuchAlgorithmException {
		ACL acl = new ACL(Perms.READ | Perms.WRITE | Perms.CREATE | Perms.DELETE, new Id("digest", DigestAuthenticationProvider.generateDigest(username + ":" + password)));

		zooKeeper.create(path, path.getBytes(), Collections.singletonList(acl), CreateMode.PERSISTENT);
	}
	
	public static void createNode(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
		zooKeeper.create("/test1", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zooKeeper.create("/test2", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		zooKeeper.create("/test3", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zooKeeper.create("/test4", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
	}
	
	public static void crud(ZooKeeper zooKeeper) throws Exception {
		zooKeeper.create("/test", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zooKeeper.delete("/test", -1);
		zooKeeper.setData("/test", "test".getBytes(), -1);
		zooKeeper.getData("/test", (e) -> {
			System.out.println(e);
		}, null);
		
		zooKeeper.exists("/test", (e) -> {
			System.out.println(e);
		});
		zooKeeper.getChildren("/test", (e) -> {
			System.out.println(e);
		});
	}
}
