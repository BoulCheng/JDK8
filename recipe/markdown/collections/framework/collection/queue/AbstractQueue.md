# AbstractQueue

- The implementations in this class are appropriate when the base implementation does not allow null elements. 

- Methods add, remove, and element are based on offer, poll, and peek, respectively, but throw exceptions instead of indicating failure via false or null returns.