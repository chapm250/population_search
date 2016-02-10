(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_16_20_1000))

;;; An answer will be a map with (at least) four entries:
;;;   * :instance
;;;   * :choices - a vector of 0's and 1's indicating whether
;;;        the corresponding item should be included
;;;   * :total-weight - the weight of the chosen items
;;;   * :total-value - the value of the chosen items

(defn included-items
  "Takes a sequences of items and a sequence of choices and
  returns the subsequence of items corresponding to the 1's
  in the choices sequence."
  [items choices]
  (map first
       (filter #(= 1 (second %))
               (map vector items choices))))

(defn random-answer
  "Construct a random answer for the given instance of the
  knapsack problem."
  [instance]
  (let [choices (repeatedly (count (:items instance))
                            #(rand-int 2))
        included (included-items (:items instance) choices)]
    {:instance instance
     :choices choices
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))}))


  ;(println (random-answer knapPI_16_20_1000_1))

;;; It might be cool to write a function that
;;; generates weighted proportions of 0's and 1's.

(defn score
  "Takes the total-weight of the given answer unless it's over capacity,
   in which case we return 0."
  [answer]
  (if (> (:total-weight answer)
         (:capacity (:instance answer)))
    0
    (:total-value answer)))

(defn add-score
  "Computes the score of an answer and inserts a new :score field
   to the given answer, returning the augmented answer."
  [answer]
  (assoc answer :score (score answer)))

(defn random-search
  [instance max-tries]
  (apply max-key :score
         (map add-score
              (repeatedly max-tries #(random-answer instance)))))

(defn find-answer
  [choice instance]
  (let [choices choice
        included (included-items (:items instance) choices)]
    {:instance instance
     :choices choices
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))}))




(defn tweak-choice
  [choice x]
  (let [conchoice (into [] choice)]
    (if (> x 0)
      (tweak-choice (assoc conchoice (rand-int (count conchoice)) (rand-int 1)) (dec x))
      (seq choice)
      )))

;;  (defn tweak-choice
;;    [choice x]
;;    (let [conchoice (into [] choice)]
;;      (if (> x 0)
;;        (let [index (rand-int (count conchoice))]
;;          (tweak-choice (assoc conchoice index (mod (+ 1 (nth conchoice index)) 2)) (dec x))
;;          (seq choice)
;;          ))))

;(println (tweak-choice (:choices (random-answer knapPI_16_20_1000_1))  3))
(tweak-choice '(1 1 1 1 1)  3)
;(max-key {:score 10} {:score 12})

(add-score (find-answer (tweak-choice (:choices (random-answer knapPI_11_20_1000_1)) 2) knapPI_11_20_1000_1)
)

(mod 7 2)

(defn hill-climb
  [winner max-tries instance]
  ; (println "winner")
  (loop [num-tries 0
         current-best winner]
    (if (>= num-tries max-tries)
      current-best
      (let [tweaked-choices (tweak-choice (:choices current-best)  10)
            new-answer (find-answer tweaked-choices instance)
            scored-new-answer (add-score new-answer)]
         (println scored-new-answer)
        (recur (inc num-tries)
               (max-key :score current-best scored-new-answer))))))

(let [start (add-score (random-answer knapPI_16_20_1000_74))
      hill-best (hill-climb start 200 knapPI_16_20_1000_74)]
  (println start)
  hill-best
)

(random-search knapPI_16_20_1000_56 2000)


