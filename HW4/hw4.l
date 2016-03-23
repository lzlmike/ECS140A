;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Your solution goes in this file ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defun all-length (ls)
  (cond 
    ((null ls) 0)
    ((atom ls) 1)
    ((listp ls)(+(all-length(car ls))(all-length(cdr ls))))))

(defun findMax (ls curMax)
    (if (null ls)
        curMax ;; Base Case:we are at the end of the list return
    (findMax(cdr ls) ;; Else call the function recursively with curMax
        (if (> (car ls) curMax) (car ls) curMax )))) ;;curMax calculation

;; Same as findMax just with slight changes        
(defun findMin (ls curMin)
    (if (null ls)
        curMin 
    (findMin(cdr ls) 
        (if (< (car ls) curMin) (car ls) curMin ))))
        
(defun range (ls)
    (list (findMin ls (car ls))(findMax ls (car ls))))
;; Just call the two min and max helper functions 

(defun before(first last ls) 
    (cond
        ((null ls)nil)
        ((equal (car ls) first) 
           (cons first (makeList (cdr ls) last)) 
        )
        (t (before first last (cdr ls)))
    )
)

(defun makeList (ls last)
    (cond 
        ((null ls) nil)
        ((equal (car ls) last)
            (cons (car ls) nil)           
        )
        (t (cons (car ls) (makeList (cdr ls) last)))
))

(defun split-if(f ls) 
    (list (seperate(lambda (x) (not (funcall f x))) ls) (seperate f ls))
)

(defun seperate (f ls)
    (cond
        ((null ls) nil)
        ((funcall f (car ls)) (cons (car ls) (seperate f (cdr ls))))
        (t (seperate f (cdr ls)))
))

;; smallGrouper think recursive for 1-n 2-n etc 

(defun smallGrouper (ls result n)
    (cond 
        ((zerop n) (list ls result))
        ((null ls) (list ls result))
        (t (smallGrouper  (cdr ls) (append result (list (car ls))) (- n 1)))
))

(defun group(ls n &optional result)
 	(if (null ls) result
		(let ((smallResult (smallGrouper ls nil n)))
			(group (car smallResult) n (append result (cdr smallResult)))
		)
))
			
(defun comparator (ls f high result)
    (if (null ls) (list result high)
        (let* ((cur (car ls)) (curScore (funcall f cur)))
            (cond 
                ((= curScore high) (comparator (cdr ls) f high (append result (list cur))))
                ((> curScore high) (comparator (cdr ls) f curScore (list cur)))
                (t(comparator (cdr ls) f high result))
))))
			
(defun mostn (f ls)
    (comparator ls f 0 nil)
)

(defun matchhelp (ls1 ls2 mat)
    (cond
        ((equal mat nil)   nil)
        ((or (and (null ls1) (not (null ls2))) (and (not(null ls1)) (null ls2))) nil)
        ((and (null ls1) (null ls2)) T)
        ((or (equal(car ls1) (car ls2))  (equal(car ls1) '?) ) (matchhelp (cdr ls1) (cdr ls2) T))
        ((and  (not (equal(car ls1) (car ls2)))  (not(or(equal(car ls1) '?) (equal (car ls1) '!))) ) (matchhelp (cdr ls1) (cdr ls2) nil))

        ((and(equal (car ls1) '!) (equal (cdr ls1) nil )) T)
        ((and(equal (car ls1) '!) (equal (cdr ls2)  nil)) nil)
        ((and(equal (car ls1) '!) (equal(cadr ls1) '?) )  (matchhelp (cdr ls1) (cdr ls2) T) )
        ((and(equal (car ls1) '!) (equal(cadr ls1) '!) )  (matchhelp (cdr ls1) (cdr ls2) T) )
        ((and(equal (car ls1) '!) (not(equal (cadr ls1) (cadr ls2))))  (matchhelp ls1 (cdr ls2) T))
        
        ((and(equal (car ls1) '!) (equal (cadr ls1) (cadr ls2)))  (matchhelp (cdr ls1) (cdr ls2) T) )
    )
)

(defun match (ls1 ls2)
 (matchhelp ls1 ls2 T)
)
	
(defun match-var (pat ass &optional batch)
	(cond
		((and (null pat) (null ass)) t)
		((or (null pat) (null ass)) nil)
		((eql (car pat) (car ass))
			(match-var (cdr pat) (cdr ass)))

		((listp (car pat))
		(cond
			((eql (car(car pat)) '?)
			(and (match-var (cdr pat) (cdr ass)) (setVar pat ass batch)t))
			((eql (car(car pat)) '!)
			    (or (and (match-var (cdr pat) (cdr ass))(setVar pat ass batch)t)
				(match-var pat (cdr ass) (append batch (list (car ass))))
			))
		  
        ))
        ((not(eql (car pat) (car ass)))nil)
))

(defun setVar (pat ass batch)
	(set (car (cdr (car pat)))(if batch(append batch (list (car ass)))(car ass))
))
			
			
