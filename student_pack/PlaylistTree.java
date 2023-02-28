import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;


public class PlaylistTree {

	public PlaylistNode primaryRoot;        //root of the primary B+ tree
	public PlaylistNode secondaryRoot;    //root of the secondary B+ tree

	public PlaylistTree(Integer order) {
		PlaylistNode.order = order;
		primaryRoot = new PlaylistNodePrimaryLeaf(null);
		primaryRoot.level = 0;
		secondaryRoot = new PlaylistNodeSecondaryLeaf(null);
		secondaryRoot.level = 0;
	}

	public void addSong(CengSong song) {
		// TODO: Implement this method
		// add methods to fill both primary and secondary tree
		addSongPrimaryIndex(song);
		addSongSecondaryIndex(song);
	}

	public CengSong searchSong(Integer audioId) {
		// TODO: Implement this method
		// find the song with the searched audioId in primary B+ tree
		// return value will not be tested, just print according to the specifications

		PlaylistNode node = primaryRoot;
		int flag = 0;
		PlaylistNodeType curr_type = PlaylistNodeType.Internal;

		while (node.getType() == curr_type) {
			for (int temp = 1; temp < node.level; temp++) {
				System.out.print("\t");
			}
			System.out.println("<index>");
			PlaylistNodePrimaryIndex curr_nodd = ((PlaylistNodePrimaryIndex) node);
			for (int i = 0; i < curr_nodd.audioIdAtIndex(i); i++) {
				for (int temp = 1; temp < node.level; temp++) {
					System.out.print("\t");
				}
				System.out.println(curr_nodd.audioIdAtIndex(i));
			}
			for (int temp = 1; temp < node.level; temp++) {
				System.out.print("\t");
			}
			System.out.println("</index>");
			flag = 0;
			PlaylistNodePrimaryIndex curr_nodd_new = ((PlaylistNodePrimaryIndex) node);
			int curr_node_count = curr_nodd_new.audioIdCount();
			for (int i = 0; i < curr_node_count; i++) {
				if (curr_nodd_new.audioIdAtIndex(i) > audioId) {
					node = curr_nodd_new.getChildrenAt(i);
					flag = 1;
					break;
				}
			}
			if (flag == 0)
				node = curr_nodd_new.getChildrenAt(curr_nodd_new.audioIdCount());
		}
		PlaylistNodePrimaryLeaf simdiki_node = ((PlaylistNodePrimaryLeaf) node);
		int curr_count_ = simdiki_node.songCount();
		for (int i = 0; i < curr_count_; i++) {
			Integer current_audioid = simdiki_node.audioIdAtIndex(i);
			if (Objects.equals(current_audioid, audioId)) {
				for (int temp = 1; temp < node.level; temp++) {
					System.out.print("\t");
				}
				System.out.println("<data>");
				for (int temp = 1; temp < node.level; temp++) {
					System.out.print("\t");
				}
				CengSong node_to_print = simdiki_node.songAtIndex(i);
				System.out.print("<record>");
				System.out.print(node_to_print.audioId());
				System.out.print("|");
				System.out.print(node_to_print.genre());
				System.out.print("|");
				System.out.print(node_to_print.songName());
				System.out.print("|");
				System.out.print(node_to_print.artist());
				System.out.print("</record>\n");
				for (int temp = 1; temp < node.level; temp++) {
					System.out.print("\t");
				}
				System.out.println("</data>");
				return (node_to_print);
			}
		}
		System.out.print("Could not find ");
		System.out.println(audioId);
		return null;
	}

	public void printPrimaryPlaylist() {
		// TODO: Implement this method
		// print the primary B+ tree in Depth-first order
		Stack<PlaylistNode> itemStack = new Stack<>();
		PlaylistNode root_of_my_tree = primaryRoot;

		itemStack.add(root_of_my_tree);

		while (!itemStack.isEmpty()) {
			PlaylistNode node = itemStack.pop();
			if (node.getType() == PlaylistNodeType.Leaf) {
				int curr_level = node.level;
				int curr_song_cnt = ((PlaylistNodePrimaryLeaf) node).songCount();
				for (int temp = 1; temp < curr_level; temp++) {
					System.out.print("\t");
				}
				System.out.println("<data>");

				for (int i = 0; i < curr_song_cnt; i++) {
					int now_level = node.level;
					for (int temp = 1; temp < now_level; temp++) {
						System.out.print("\t");
					}
					System.out.print("<record>");
					System.out.print(((PlaylistNodePrimaryLeaf) node).songAtIndex(i).audioId());
					System.out.print("|");
					System.out.print(((PlaylistNodePrimaryLeaf) node).songAtIndex(i).genre());
					System.out.print("|");
					System.out.print(((PlaylistNodePrimaryLeaf) node).songAtIndex(i).songName());
					System.out.print("|");
					System.out.print(((PlaylistNodePrimaryLeaf) node).songAtIndex(i).artist());
					System.out.print("</record>\n");
				}
				int scnd_now_lvl = node.level;
				for (int temp = 1; temp < scnd_now_lvl; temp++) {
					System.out.print("\t");
				}
				System.out.println("</data>");
			}
			else if (node.getType() == PlaylistNodeType.Internal) {
				ArrayList<PlaylistNode> children = ((PlaylistNodePrimaryIndex) node).getAllChildren();
				int chld_size = children.size();
				int curr_level = node.level;
				for (int i = chld_size - 1; i >= 0; i--)
					itemStack.add(children.get(i));

				for (int temp = 1; temp < curr_level; temp++) {
					System.out.print("\t");
				}
				System.out.println("<index>");
				for (int i = 0; i < ((PlaylistNodePrimaryIndex) node).audioIdCount(); i++) {
					int curr_level_ = node.level;
					for (int temp = 1; temp < curr_level_; temp++) {
						System.out.print("\t");
					}
					System.out.println(((PlaylistNodePrimaryIndex) node).audioIdAtIndex(i));
				}
				int curr_level__ = node.level;
				for (int temp = 1; temp < curr_level__; temp++) {
					System.out.print("\t");
				}
				System.out.println("</index>");
			}
		}
	}

	public void printSecondaryPlaylist() {
		// TODO: Implement this method
		// print the secondary B+ tree in Depth-first order
		Stack<PlaylistNode> itemStack = new Stack<PlaylistNode>();
		PlaylistNode root_of_my_tree = secondaryRoot;

		itemStack.add(root_of_my_tree);

		while (!itemStack.isEmpty()) {
			PlaylistNode node = itemStack.pop();
			if (node.getType() == PlaylistNodeType.Leaf) {
				for (int temp = 1; temp < node.level; temp++) {
					System.out.print("\t");
				}
				System.out.println("<data>");

				for (int i = 0; i < ((PlaylistNodeSecondaryLeaf) node).getSongBucket().size(); i++) {
					for (int temp = 1; temp < node.level; temp++) {
						System.out.print("\t");
					}
					System.out.println(((PlaylistNodeSecondaryLeaf) node).genreAtIndex(i));
					for (int j = 0; j < ((PlaylistNodeSecondaryLeaf) node).songsAtIndex(i).size(); j++) {
						for (int temp = 1; temp < node.level + 1; temp++) {
							System.out.print("\t");
						}
						System.out.print("<record>");
						System.out.print(((PlaylistNodeSecondaryLeaf) node).songsAtIndex(i).get(j).audioId());
						System.out.print("|");
						System.out.print(((PlaylistNodeSecondaryLeaf) node).songsAtIndex(i).get(j).genre());
						System.out.print("|");
						System.out.print(((PlaylistNodeSecondaryLeaf) node).songsAtIndex(i).get(j).songName());
						System.out.print("|");
						System.out.print(((PlaylistNodeSecondaryLeaf) node).songsAtIndex(i).get(j).artist());
						System.out.print("</record>\n");
					}
				}
				for (int temp = 1; temp < node.level; temp++) {
					System.out.print("\t");
				}
				System.out.println("</data>");
			}
			else if (node.getType() == PlaylistNodeType.Internal) {
				ArrayList<PlaylistNode> children = ((PlaylistNodeSecondaryIndex) node).getAllChildren();
				for (int i = children.size() - 1; i >= 0; i--)
					itemStack.add(children.get(i));

				for (int temp = 1; temp < node.level; temp++) {
					System.out.print("\t");
				}
				System.out.println("<index>");
				for (int i = 0; i < ((PlaylistNodeSecondaryIndex) node).genreCount(); i++) {
					for (int temp = 1; temp < node.level; temp++) {
						System.out.print("\t");
					}
					System.out.print(((PlaylistNodeSecondaryIndex) node).genreAtIndex(i));
					System.out.print("\n");
				}
				for (int temp = 1; temp < node.level; temp++) {
					System.out.print("\t");
				}
				System.out.println("</index>");
			}
		}
	}
	// Extra functions if needed
	public void addSongPrimaryIndex(CengSong song) {
		PlaylistNode node = primaryRoot;
		PlaylistNode update_node = primaryRoot;
		int flag = 0;
		int limitsongcount = (2 * PlaylistNode.order) + 1;

		while (node.getType() == PlaylistNodeType.Internal) {
			int check_condition = ((PlaylistNodePrimaryIndex) node).audioIdCount();
			flag = 0;
			for (int i = 0; i < check_condition; i++) {
				if (((PlaylistNodePrimaryIndex) node).audioIdAtIndex(i) > song.audioId()) {
					update_node = ((PlaylistNodePrimaryIndex) node).getChildrenAt(i);
					flag = 1;
					break;
				}
			}
			if (flag == 0)
				update_node = ((PlaylistNodePrimaryIndex) node).getChildrenAt(((PlaylistNodePrimaryIndex) node).audioIdCount());

			node = update_node;
		}

		PlaylistNodePrimaryLeaf leaf_found = ((PlaylistNodePrimaryLeaf) node);
		ArrayList<CengSong> songsOfPlaylist = leaf_found.getSongs();

		int controll = leaf_found.songCount();
		int my_index = controll;
		int i1=0;
		while(i1 < controll){
			Integer cmp1 = songsOfPlaylist.get(i1).audioId();
			Integer cmp2 = song.audioId();
			if (cmp1>cmp2)
			{
				my_index = i1;
				break;
			}
			i1++;
		}

		leaf_found.addSong(my_index, song);

		if (leaf_found.getParent() != null) {
			if (leaf_found.songCount() == limitsongcount) {
				PlaylistNode parent = leaf_found.getParent();
				PlaylistNodePrimaryLeaf create_new_node = new PlaylistNodePrimaryLeaf(parent);
				int ind_ = PlaylistNode.order;
				int ind_2 = 0;
				int sng_cnt_ = leaf_found.songCount();
				while (ind_ < sng_cnt_) {
					create_new_node.addSong(ind_2, leaf_found.songAtIndex(ind_));
					ind_2++;
					ind_++;
				}
				leaf_found.getSongs().subList(PlaylistNode.order, leaf_found.getSongs().size()).clear();
				Integer going_up = create_new_node.audioIdAtIndex(0);
				int flag_second = 0;
				int how_many_audios = ((PlaylistNodePrimaryIndex) parent).audioIdCount();
				int ii=0;
				while(ii < how_many_audios){
					if (((PlaylistNodePrimaryIndex) parent).audioIdAtIndex(ii) > going_up) {
						flag_second = 1;
						((PlaylistNodePrimaryIndex) parent).addSongId(ii, going_up);
						((PlaylistNodePrimaryIndex) parent).addChild(ii + 1, create_new_node);
						break;
					}
					ii++;
				}
				if (flag_second == 0) {
					((PlaylistNodePrimaryIndex) parent).addSongId(going_up);
					((PlaylistNodePrimaryIndex) parent).addChild(create_new_node);
				}

				PlaylistNode upper_parent;
				while (((PlaylistNodePrimaryIndex) parent).audioIdCount() == limitsongcount) {
					flag_second = 0;

					upper_parent = parent.getParent();

					if (upper_parent != null) {
						PlaylistNodePrimaryIndex create_new_internal_node = new PlaylistNodePrimaryIndex(upper_parent);
						int ordpl = PlaylistNode.order + 1;
						int jtemp = 0;
						int hw_manyy = ((PlaylistNodePrimaryIndex) parent).audioIdCount();
						while (ordpl < hw_manyy) {
							create_new_internal_node.addSongId(jtemp, ((PlaylistNodePrimaryIndex) parent).audioIdAtIndex(ordpl));
							create_new_internal_node.addChild(jtemp, ((PlaylistNodePrimaryIndex) parent).getChildrenAt(ordpl));
							((PlaylistNodePrimaryIndex) parent).getChildrenAt(ordpl).setParent(create_new_internal_node);
							ordpl++;
							jtemp++;
						}

						int child_to_where = create_new_internal_node.audioIdCount();
						int get_from = ((PlaylistNodePrimaryIndex) parent).audioIdCount();
						PlaylistNode needed_chld = ((PlaylistNodePrimaryIndex) parent).getChildrenAt(get_from);
						create_new_internal_node.addChild(child_to_where, needed_chld);
						((PlaylistNodePrimaryIndex) parent).getChildrenAt(((PlaylistNodePrimaryIndex) parent).audioIdCount()).setParent(create_new_internal_node);

						int my_ord = PlaylistNode.order;
						going_up = (((PlaylistNodePrimaryIndex) parent)).audioIdAtIndex(my_ord);

						((PlaylistNodePrimaryIndex) parent).takeAllSongIds().subList(my_ord, ((PlaylistNodePrimaryIndex) parent).takeAllSongIds().size()).clear();
						((PlaylistNodePrimaryIndex) parent).getAllChildren().subList(my_ord + 1, ((PlaylistNodePrimaryIndex) parent).getAllChildren().size()).clear();

						int lmttt = ((PlaylistNodePrimaryIndex) upper_parent).audioIdCount();
						int i_i = 0;
						while(i_i < lmttt) {
							if ( going_up < ((PlaylistNodePrimaryIndex) upper_parent).audioIdAtIndex(i_i) ) {
								((PlaylistNodePrimaryIndex) upper_parent).addSongId(i_i, going_up);
								((PlaylistNodePrimaryIndex) upper_parent).addChild(i_i, parent);
								((PlaylistNodePrimaryIndex) upper_parent).putChild(i_i + 1, create_new_internal_node);
								flag_second = 1;
								break;
							}
							i_i++;
						}
						if (flag_second == 0) {
							((PlaylistNodePrimaryIndex) upper_parent).addSongId(going_up);
							((PlaylistNodePrimaryIndex) upper_parent).addChild(create_new_internal_node);
						}
						parent = upper_parent;
					}
					if (upper_parent == null) {
						PlaylistNodePrimaryIndex create_new_root = new PlaylistNodePrimaryIndex(null);
						PlaylistNodePrimaryIndex create_new_internal_node = new PlaylistNodePrimaryIndex(create_new_root);
						int indx_ = PlaylistNode.order + 1;
						int indx_2 = 0;
						int how_mny = ((PlaylistNodePrimaryIndex) parent).audioIdCount();
						while (indx_ < how_mny) {
							create_new_internal_node.addSongId(indx_2, ((PlaylistNodePrimaryIndex) parent).audioIdAtIndex(indx_));
							create_new_internal_node.addChild(indx_2, ((PlaylistNodePrimaryIndex) parent).getChildrenAt(indx_));
							((PlaylistNodePrimaryIndex) parent).getChildrenAt(indx_).setParent(create_new_internal_node);
							indx_ += 1;
							indx_2 += 1;
						}
						int child_to_where = create_new_internal_node.audioIdCount();
						int get_from = ((PlaylistNodePrimaryIndex) parent).audioIdCount();
						PlaylistNode needed_chld = ((PlaylistNodePrimaryIndex) parent).getChildrenAt(get_from);
						create_new_internal_node.addChild(child_to_where, needed_chld);
						((PlaylistNodePrimaryIndex) parent).getChildrenAt(((PlaylistNodePrimaryIndex) parent).audioIdCount()).setParent(create_new_internal_node);

						int my_ord = PlaylistNode.order;
						going_up = ((PlaylistNodePrimaryIndex) parent).audioIdAtIndex(my_ord);

						((PlaylistNodePrimaryIndex) parent).takeAllSongIds().subList(my_ord, ((PlaylistNodePrimaryIndex) parent).takeAllSongIds().size()).clear();
						((PlaylistNodePrimaryIndex) parent).getAllChildren().subList(my_ord + 1, ((PlaylistNodePrimaryIndex) parent).getAllChildren().size()).clear();

						create_new_root.addSongId(0, going_up);
						create_new_root.addChild(0, parent);
						parent.setParent(create_new_root);
						create_new_root.addChild(1, create_new_internal_node);
						primaryRoot = create_new_root;
						break;
					}
				}

			}
		}
		if (leaf_found.getParent() == null) {
			if (leaf_found.songCount() == limitsongcount) {
				PlaylistNodePrimaryIndex create_root = new PlaylistNodePrimaryIndex(null);
				PlaylistNodePrimaryLeaf create_node = new PlaylistNodePrimaryLeaf(create_root);
				int j = 0;
				int i = PlaylistNode.order;
				int cntrl = leaf_found.songCount();
				while (i < cntrl) {
					create_node.addSong(j, leaf_found.songAtIndex(i));
					i++;
					j++;
				}
				leaf_found.getSongs().subList(PlaylistNode.order, leaf_found.getSongs().size()).clear();
				leaf_found.setParent(create_root);
				create_root.addSongId(0, create_node.songAtIndex(0).audioId());
				create_root.addChild(0, leaf_found);
				create_root.addChild(1, create_node);
				primaryRoot = create_root;
			}
		}
	}

	public void addSongSecondaryIndex(CengSong song) {
		PlaylistNode node = secondaryRoot;
		int flag;

		while (node.getType() == PlaylistNodeType.Internal)
		{
			flag = 0;
			PlaylistNodeSecondaryIndex curr_node = (PlaylistNodeSecondaryIndex) node;
			int curr_genre_count = curr_node.genreCount();
			for (int i = 0; i < curr_genre_count ; i++) {
				if (curr_node.genreAtIndex(i).compareTo(song.genre())>0)
				{
					node = curr_node.getChildrenAt(i);
					flag = 1;
					break;
				}
				else if (curr_node.genreAtIndex(i).equals(song.genre()))
				{
					node = curr_node.getChildrenAt(i+1);
					flag = 1;
					break;
				}
			}

			if (flag==0)
				node = curr_node.getChildrenAt(curr_genre_count);
		}

		PlaylistNodeSecondaryLeaf leaf_placed = (PlaylistNodeSecondaryLeaf) node;
		ArrayList<ArrayList<CengSong>> buckets = leaf_placed.getSongBucket();

		int size_of_the_bucket = buckets.size();
		int index_found = size_of_the_bucket;

		for (int i = 0; i < size_of_the_bucket; i++) {
			String first_genre = buckets.get(i).get(0).genre();
			if (first_genre.equals(song.genre()) || first_genre.compareTo(song.genre()) > 0) {
				index_found = i;
				break;
			}
		}

		leaf_placed.addSong(index_found, song);

		// if it is a leaf
		if(leaf_placed.getParent() != null) {
			// check overflow here
			int order_current = PlaylistNode.order;
			int when_overflow = 2 * order_current + 1;
			if (leaf_placed.genreCount() == when_overflow) {
				PlaylistNode parent = leaf_placed.getParent();
				PlaylistNodeSecondaryLeaf create_new_node = new PlaylistNodeSecondaryLeaf(parent);
				int index_ = order_current;
				int holder = 0;
				int genre_cnt_of_lf = leaf_placed.genreCount();
				while(index_ < genre_cnt_of_lf) {
					int song_count = leaf_placed.songsAtIndex(index_).size();
					ArrayList<CengSong> temp_songs = leaf_placed.songsAtIndex(index_);
					for (int k = 0; k < song_count; k++) {
						create_new_node.addSong(holder, temp_songs.get(k));
					}
					index_++;
					holder++;
				}

				// silincek yerler var unutma!!!!!!!!
				leaf_placed.getSongBucket().subList(order_current, leaf_placed.getSongBucket().size()).clear();

				// yukari tasicam
				String genreToUp = create_new_node.genreAtIndex(0);
				int cont2 = 0;
				PlaylistNodeSecondaryIndex parent_of_curr = (PlaylistNodeSecondaryIndex) parent;
				int genre_cnt_of_parent = parent_of_curr.genreCount();
				for (int i = 0; i < genre_cnt_of_parent; i++) {
					if (((PlaylistNodeSecondaryIndex) parent).genreAtIndex(i).compareTo(genreToUp) >= 0) {
						((PlaylistNodeSecondaryIndex) parent).addGenre(i, genreToUp);
						((PlaylistNodeSecondaryIndex) parent).addChild(i + 1, create_new_node);
						cont2 = 1;
						break;
					}
				}

				if (cont2==0) {
					((PlaylistNodeSecondaryIndex) parent).addGenre(genreToUp);
					((PlaylistNodeSecondaryIndex) parent).addChild(create_new_node);
				}

				// check overflow here
				PlaylistNode upper_parent;
				while (((PlaylistNodeSecondaryIndex) parent).genreCount() == when_overflow) {
					upper_parent = parent.getParent();

					if(upper_parent != null)
					{
						PlaylistNodeSecondaryIndex create_new_internal_node = new PlaylistNodeSecondaryIndex(upper_parent);

						int strt = PlaylistNode.order + 1;
						int stp = ((PlaylistNodeSecondaryIndex)parent).genreCount();
						int ilerle = 0;
						while(strt<stp)
						{
							// Here, parent refers to old internal node (sibling of create_new_internal_node)
							create_new_internal_node.addGenre(ilerle,((PlaylistNodeSecondaryIndex)parent).genreAtIndex(strt));
							create_new_internal_node.addChild(ilerle,((PlaylistNodeSecondaryIndex)parent).getChildrenAt(strt));
							((PlaylistNodeSecondaryIndex)parent).getChildrenAt(strt).setParent(create_new_internal_node);
							strt++;
							ilerle++;
						}
						int child_to_where = create_new_internal_node.genreCount();
						int get_from_where = ((PlaylistNodeSecondaryIndex)parent).genreCount();
						PlaylistNode get_The_chl = ((PlaylistNodeSecondaryIndex)parent).getChildrenAt(get_from_where);
						create_new_internal_node.addChild(child_to_where,get_The_chl);((PlaylistNodeSecondaryIndex)parent).getChildrenAt(((PlaylistNodeSecondaryIndex)parent).genreCount()).setParent(create_new_internal_node);

						int my_ordr = PlaylistNode.order;
						genreToUp = ((PlaylistNodeSecondaryIndex)parent).genreAtIndex(my_ordr);

						// sil
						((PlaylistNodeSecondaryIndex)parent).takeAllGenres().subList(my_ordr, ((PlaylistNodeSecondaryIndex)parent).takeAllGenres().size()).clear();
						((PlaylistNodeSecondaryIndex)parent).getAllChildren().subList(my_ordr + 1, ((PlaylistNodeSecondaryIndex)parent).getAllChildren().size()).clear();

						int cont3 = 0;
						int loop_var = 0;
						int cnt_of_next_parent =  ((PlaylistNodeSecondaryIndex) upper_parent).genreCount();
						while(loop_var<cnt_of_next_parent) {
							if (((PlaylistNodeSecondaryIndex) upper_parent).genreAtIndex(loop_var).compareTo(song.genre())>0)
							{
								((PlaylistNodeSecondaryIndex)upper_parent).addGenre(loop_var, genreToUp);
								((PlaylistNodeSecondaryIndex)upper_parent).addChild(loop_var, parent);
								((PlaylistNodeSecondaryIndex)upper_parent).putChild(loop_var + 1, create_new_internal_node);
								cont3 = 1;
								break;
							}
							else if (((PlaylistNodeSecondaryIndex) upper_parent).genreAtIndex(loop_var).equals(song.genre()))
							{
								((PlaylistNodeSecondaryIndex)upper_parent).addGenre(loop_var, genreToUp);
								((PlaylistNodeSecondaryIndex)upper_parent).addChild(loop_var, parent);
								((PlaylistNodeSecondaryIndex)upper_parent).putChild(loop_var + 1, create_new_internal_node);
								cont3 = 1;
								break;
							}
							loop_var++;
						}

						if (cont3==0) {
							((PlaylistNodeSecondaryIndex) upper_parent).addGenre(genreToUp);
							((PlaylistNodeSecondaryIndex) upper_parent).addChild(create_new_internal_node);
						}
						parent = upper_parent;
					}
					// eger roottaysam
					else if (upper_parent == null)
					{
						PlaylistNodeSecondaryIndex create_new_root = new PlaylistNodeSecondaryIndex(null);
						PlaylistNodeSecondaryIndex create_new_internal_node = new PlaylistNodeSecondaryIndex(create_new_root);
						int tmp = order_current+1;
						int tmp2 = 0;
						int curr_genre_cnt = ((PlaylistNodeSecondaryIndex)parent).genreCount();
						while(tmp<curr_genre_cnt)
						{
							//	System.out.println(((PlaylistNodeSecondaryIndex)parent).genreAtIndex(index));
							create_new_internal_node.addGenre(tmp2,((PlaylistNodeSecondaryIndex)parent).genreAtIndex(tmp));
							create_new_internal_node.addChild(tmp2,((PlaylistNodeSecondaryIndex)parent).getChildrenAt(tmp));
							((PlaylistNodeSecondaryIndex)parent).getChildrenAt(tmp).setParent(create_new_internal_node);
							tmp++;
							tmp2++;
						}
						int child_to_where = create_new_internal_node.genreCount();
						int get_from_where = ((PlaylistNodeSecondaryIndex)parent).genreCount();
						PlaylistNode get_The_chl = ((PlaylistNodeSecondaryIndex)parent).getChildrenAt(get_from_where);
						create_new_internal_node.addChild(child_to_where,get_The_chl);
						((PlaylistNodeSecondaryIndex)parent).getChildrenAt(((PlaylistNodeSecondaryIndex)parent).genreCount()).setParent(create_new_internal_node);

						genreToUp = ((PlaylistNodeSecondaryIndex)parent).genreAtIndex(order_current);

						// clear
						((PlaylistNodeSecondaryIndex)parent).takeAllGenres().subList(order_current, ((PlaylistNodeSecondaryIndex)parent).takeAllGenres().size()).clear();
						((PlaylistNodeSecondaryIndex)parent).getAllChildren().subList(order_current + 1, ((PlaylistNodeSecondaryIndex)parent).getAllChildren().size()).clear();
						// gerekli yerlri add
						create_new_root.addGenre(0, genreToUp);
						create_new_root.addChild(0, parent);
						parent.setParent(create_new_root);
						create_new_root.addChild(1, create_new_internal_node);
						secondaryRoot = create_new_root;
						break;
					}
				}
			}
		}
		// now i am at root
		else if (leaf_placed.getParent() == null) {

			// check overflow here
			int order_current = PlaylistNode.order;
			int when_overflow = 2 * order_current + 1;

			if (leaf_placed.genreCount() == when_overflow)
			{
				PlaylistNodeSecondaryIndex create_new_root = new PlaylistNodeSecondaryIndex(null);
				PlaylistNodeSecondaryLeaf create_new_node = new PlaylistNodeSecondaryLeaf(create_new_root);

				Integer index_ = order_current;
				int j = 0;
				int genre_cnt_of_lf = leaf_placed.genreCount();
				while(index_ < genre_cnt_of_lf){
					int song_count = leaf_placed.songsAtIndex(index_).size();
					ArrayList<CengSong> temp_songs = leaf_placed.songsAtIndex(index_);
					for (int k = 0; k < song_count; k++) {
						create_new_node.addSong(j, temp_songs.get(k));
					}
					index_++;
					j++;
				}
				leaf_placed.getSongBucket().subList(order_current, leaf_placed.getSongBucket().size()).clear();
				leaf_placed.setParent(create_new_root);
				create_new_root.addGenre(0, create_new_node.songsAtIndex(0).get(0).genre());
				create_new_root.addChild(0, leaf_placed);
				create_new_root.addChild(1, create_new_node);
				secondaryRoot = create_new_root;
			}
		}

	}
}




