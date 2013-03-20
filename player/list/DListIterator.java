/* DListIterator.java */

package list;

/**
 *  A DListIterator defines an iterator for DList
 *
 **/

public class DListIterator extends Iterator {

  private DListNode curr;

  public DListIterator(DListNode head) {
      this.curr = head.next;
  }

  public boolean hasNext() {
    if (curr.isValidNode()) {
      return true;
    }
    else {
      return false;
    }
  }

  public next() {
    DListNode temp = curr.next();
    curr = curr.next();
    return temp;
  }
}
