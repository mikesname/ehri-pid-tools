package models

trait Page[T] {
  def items: Seq[T]
  def page: Int
  def total: Int
  def numPages: Int
  def hasMultiplePages: Boolean = numPages > 1
  def hasMore: Boolean = page < numPages

  /*
   * Helper to provide Digg-style pagination, like:
   *    1, 2 ... 18, 19, 20, 21, 22 ... 55, 56
   * Logic borrowed from here:
   *   http://www.strangerstudios.com/sandbox/pagination/diggstyle_code.txt
   */
  def paginationRanges(adjacents: Int = 3): List[Range] = {
    val page = this.page + 1
    val window = adjacents * 2
    numPages match {
      // Last page is the same as single page... no ranges
      case lp if lp <= 1 => Nil
      // Not enough pages to bother hiding any...
      case lp if lp < 7 + window =>
        List(1 to lp)
      // Close to start, so only hide later pages
      case lp if lp > 5 + window && page < 1 + window =>
        List(1 until (4 + window), (lp - 1) to lp)
      // Around the middle, hide both start and end pages
      case lp if lp - window > page && page > window =>
        List(1 to 2, (page - adjacents) to (page + adjacents), (lp - 1) to lp)
      // Close to end, hide beginning pages...
      case lp =>
        List(1 to 2, (lp - (2 + window)) to lp)
    }
  }
}
