/**
 * Copyright (c) 2015, adar.w (adar.w@outlook.com) 
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

import java.io.IOException;
import java.util.concurrent.Phaser;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Zookeeper {

	private static final String HOST = "192.168.0.107:2181,192.168.0.107:2182,192.168.0.107:2183";
	
	public static void main(String[] args) throws IOException {
		System.out.println(getClient().getState());
	}
	
	public static ZooKeeper getClient() throws IOException {
		Phaser phaser = new Phaser(1);
		
		ZooKeeper zooKeeper = new ZooKeeper(HOST, 5000, (e) -> {
			phaser.arrive();
		});
		
		phaser.awaitAdvance(phaser.getPhase());
		
		return zooKeeper;
	}
	
	public static void createNode(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
		zooKeeper.create("/test1", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zooKeeper.create("/test2", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		zooKeeper.create("/test3", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zooKeeper.create("/test4", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
	}
}
