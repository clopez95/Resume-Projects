#lang racket
(require racket/trace)

(define (parser x)
  (define lst(string->list(file->string x)))
  (program (scanner lst)))

;(trace parser)


;--------------------------------------------------SCANNER SECTION---------------------------------------------------------
(define (scannerError)
  (define line (number->string(lineNum)))
  (printf "Scanner error on line ")
  (printf line)(exit))


(define (assignOp x)
  (if(and(equal? (first(rest x)) #\=)(equal?(first(rest(rest x))) #\space))
     (cons "assignOP" (scanner (rest(rest x))))
     (scannerError)))

(define (addOp x)
  (if(equal? (first(rest x)) #\space)
     (cons "+" (scanner (rest(rest x))))
     (scannerError)))

(define (divisionOp x)
  (if(equal? (first(rest x)) #\space)
     (cons "/" (scanner (rest(rest x))))
     (scannerError)))

(define (minusOp x)
  (if(equal? (first(rest x)) #\space)
     (cons "-" (scanner (rest(rest x))))
     (scannerError)))

(define (multOp x)
  (if(equal? (first(rest x)) #\space)
     (cons "*" (scanner (rest(rest x))))
     (scannerError)))

(define (leftP x)
     (cons "leftP" (scanner (rest x))))

(define (rightP x)
  (cons "rightP" (scanner (rest x))))

(define (num x)
  (if(or(char-numeric? (first(rest x)))(equal? (first(rest x)) #\.))
     (num (rest x))
     (cons "Num" (scanner (rest x)))))


(define (keywordRead x)
  (if(and(equal? (first x) #\e)
         (equal? (first(rest x)) #\a)
         (equal? (first(rest(rest x))) #\d)
         (equal? (first(rest(rest(rest x)))) #\space))
     (cons "read" (scanner(rest(rest(rest(rest x))))))
     (checkId (rest x))))

(define (keywordWrite x)
    (if(and(equal? (first x) #\r)
         (equal? (first(rest x)) #\i)
         (equal? (first(rest(rest x))) #\t)
         (equal? (first(rest(rest(rest x)))) #\e)
         (equal? (first(rest(rest(rest (rest x))))) #\space))
     (cons "write" (scanner(rest(rest(rest(rest (rest x)))))))
     (checkId (rest x))))

(define (checkId x)
  (if(or(empty? x) (equal? (first x) #\space) (equal? (first x) #\))(equal? (first x) #\newline))
      (cons "ID" (scanner x))
     (checkId (rest x))))

(define (counter (count 0) (add 1))
  (lambda () (set! count (+ add count))
    count))
(define lineNum(counter))

(define (scanner x)
  (cond
    [(or(empty? x) (and(equal? (first x) #\$) (equal? (first(rest x)) #\$))) (cons "$$" '())]
    [(equal? (first x) #\return) (scanner (rest x))]
    [(equal? (first x) #\space) (scanner (rest x))]
    [(equal? (first x) #\newline) (cons (lineNum) (scanner (rest x)))]
    [(equal? (first x) #\:) (assignOp x)]
    [(equal? (first x) #\+) (addOp x)]
    [(equal? (first x) #\-) (minusOp x)]
    [(equal? (first x) #\*) (multOp x)]
    [(equal? (first x) #\/) (divisionOp x)]
    [(equal? (first x) #\() (leftP x)]
    [(equal? (first x) #\)) (rightP x)]
    [(char-numeric? (first x)) (num x)]
    [(char-alphabetic? (first x))(cond
                                   [(equal? (first x) #\r) (keywordRead (rest x))]
                                   [(equal? (first x) #\w) (keywordWrite (rest x))]
                                   [else (checkId x)])]
 
    [(scannerError)]))
;(trace scanner)
;(trace assignOp)
;(trace minusOp)
;(trace multOp)
;(trace divisionOp)
;(trace leftP)
;(trace rightP)
;(trace num)
;(trace keywordRead)
;(trace keywordWrite)
;(trace checkId)
;(trace counter)
;(trace scannerError)

;--------------------------------------------------PARSER SECTION---------------------------------------------------------

(define (parserError x)
  (if(integer? (first x))
     (parserErrorMessage x)
     (parserError (rest x))))

(define (parserErrorMessage x)
  (define lineNum (number->string(first x)))
  (printf "Syntax error on line ")
  (printf lineNum) (exit))

(define (parserMultOp x)
  (cond
    [(equal? (first x) "*") (rest x)]
    [(equal? (first x) "/") (rest x)]
    [(parserError x)]))


(define (parserAddOp x)
  (cond
    [(equal? (first x) "+") (rest x)]
    [(equal? (first x) "-") (rest x)]
    [(parserError x)]))

;(* or /) (epsilon)
(define (factor_tail x)
  (cond
    [(integer? (first x)) (factor_tail(rest x))]
    [(or(equal? (first x) "*") (equal? (first x) "/")) (factor_tail(factor (parserMultOp x)))]
    [(or(equal? (first x) "+") (equal? (first x) "-") (equal? (first x) "rightP")
        (equal? (first x) "ID") (equal? (first x) "read")(equal? (first x) "write")
    (equal? (first x) "$$")) x ]
    [(error x)]))

(define (matchRightP x)
  (cond
    [(equal? (first x) "rightP") (rest x)]
    [(parserError x)]))
  
(define (factor x)
  (cond
    [(equal? (first x) "leftP") (matchRightP(expr(rest x))) ]
    [(equal? (first x) "ID") (rest x)]
    [(equal? (first x) "Num") (rest x)]
    [(parserError x)]))

(define (term_tail x)
  (cond
    [(integer? (first x)) (term_tail(rest x))]
    [(or(equal? (first x) "+") (equal? (first x) "-")) (term_tail(term (parserAddOp x)))]
    [(or(equal? (first x) "rightP") (equal? (first x) "ID")
        (equal? (first x) "read") (equal? (first x) "write") (equal? (first x) "$$")) x ]
   [(parserError x)]))

(define (term x)
  (cond
    [(or(equal? (first x) "leftP") (equal? (first x) "ID")
        (equal? (first x) "Num")) (factor_tail(factor x))]
    [(parserError x)]))

; ( ID Num
(define (expr x)
  (cond
    [(or(equal? (first x) "leftP") (equal? (first x) "ID") (equal? (first x) "Num")) (term_tail (term x))]
    [(integer? (first x)) (rest x)]
    [(parserError x)]))
    
(define (stmt x)
  (cond
    [(and(equal? (first x) "ID") (equal? (first(rest x)) "assignOP")) (expr (rest(rest x)))]
    [(and(equal? (first x) "read") (equal? (first(rest x)) "ID")) (rest(rest x))]
    [(equal? (first x) "write") (expr (rest x))]
    [(integer? (first x)) (rest x)]
    [(parserError x)]))


(define (stmt_list x)
  (cond
    [(integer? (first x)) (stmt_list (rest x))]
    [(equal? (first x) "$$") (program x)]
    [(or(equal? (first x) "ID") (equal? (first x) "read") (equal? (first x) "write")) (stmt_list(stmt x))]
    [(parserError x)]))



(define (program x) 
  (cond
    [(or(equal? (first x) "ID") (equal? (first x) "read") (equal? (first x) "write")) (stmt_list x)]
    [(equal? (first x) "$$") (printf "Accepted! End of parsing, no errors")]
    [(parserError x)]))

;(trace program)
;(trace stmt_list)
;(trace stmt)
;(trace expr)
;(trace term)
;(trace term_tail)
;(trace factor)
;(trace factor_tail)
;(trace parserAddOp)
;(trace parserMultOp)
;(trace parserError)
;(trace parserErrorMessage)
;(trace matchRightP)


