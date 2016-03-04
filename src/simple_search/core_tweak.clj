(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_11_200_1000
        simple-search.knapsack-examples.knapPI_11_1000_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_13_200_1000
        simple-search.knapsack-examples.knapPI_13_1000_1000
        simple-search.knapsack-examples.knapPI_16_20_1000
        simple-search.knapsack-examples.knapPI_16_200_1000
        simple-search.knapsack-examples.knapPI_16_1000_1000))

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


(defn score
  "Takes the total-weight of the given answer unless it's over capacity,
   in which case we return over capacity * -1"
  [answer]
  (if (> (:total-weight answer)
         (:capacity (:instance answer)))
    (- (:capacity (:instance answer)) (:total-weight answer))

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
   "Takes a choices vector and a problem instance, returns a reconstructed profile of
    the contents, value and weight of the solution's knapsack."
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
      (tweak-choice (assoc conchoice (rand-int (count conchoice)) (rand-int 2)) (dec x))
      (seq choice)
      )))

(defn pick-one
  "Takes two possible outputs, randomly returns either"
  [a b]
  (if (= (rand-int 2) 0)
    a
    b))

(defn create-start-population
  "Takes a problem instance, "
  [instance]
  (repeatedly 100 #(add-score (random-answer instance)))
  )

(defn pick-folks
   "Takes a population, returns two parents for the next generation based on score"
  [population ]
  (take-last 2 (sort-by :score population))
  )

(defn random-pick-folks
  [population]
  (take-last 2 (sort-by :score (repeatedly 10 #(nth population (rand-int (count population)))))
))


(defn splice
  "Takes a choices vector and 2 indices, returns the vector segemented by the given indices."
  [choices index1 index2]
  (drop index1 (drop-last (- (count choices) index2) choices)))


(defn pick-winner
  [population ]
  (last (sort-by :score population))
  )

(defn pick-points
  [choices]
  (let [firstpoint (rand-int  (count choices))]
  (cons firstpoint (cons (+ firstpoint (rand-int (- (count choices) firstpoint))) '()))
))


(defn uniform_crossover
  [instance folks]
"Takes a problem instance and parents, returns profile "
   (let [choices  (map pick-one (:choices (first folks)) (:choices (last folks)))
         included (included-items (:items instance) choices)]
     (print choices)
    {:instance instance
     :choices choices
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))})
  )


;take a first part of parent one, second part of parent two, and third part of parent one. children come from different parents
(defn twopoint_crossover
  [instance folks]
   (let [points (pick-points (first folks))
         choices  (concat (splice (:choices (first folks)) 0 (first points)) (splice (:choices (second folks)) (first points) (second points)) (splice (:choices (first folks)) (second points) (count (:choices (first folks)))))
         included (included-items (:items instance) choices)]
    {:instance instance
     :choices choices
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))})
  )




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; This sucks right now
;; (defn new-generation
;;   "Takes a number of offspring to generate, a cross-over strategy, a problem instance and a population."
;;   [num-children crossovertype instance population]
;;   ;(print (:score (last population)))
;;   (let [folks (random-pick-folks population)]
;;     (concat folks (repeatedly num-children #(add-score (crossovertype instance folks))))))

(defn new-generation
  "Takes a number of offspring to generate, a cross-over strategy, a problem instance and a population."
  [num-children crossovertype instance population]
  (print (:score (last population)))
  (let [folks (random-pick-folks population)]
    (repeatedly num-children #(add-score (crossovertype instance (random-pick-folks population))))))


;(count (new-generation (create-start-population knapPI_16_20_1000_1) 8 knapPI_16_20_1000_1))
(defn crossover
  "Takes a problem instance, max # of attempts, and a cross-over strategy,
   returns the individual solution with the highest score"
  [instance max-tries crossovertype]
  (let [population (create-start-population instance)]
    (pick-winner (last (take max-tries (iterate (partial new-generation 100 crossovertype instance) population))))))

(crossover knapPI_16_20_1000_1 100 twopoint_crossover)

;(map pick-one (first population) (last population))

(crossover knapPI_16_20_1000_1 100 uniform_crossover)
;(count (new-generation (create-start-population knapPI_16_20_1000_1) 8 knapPI_16_20_1000_1))
;(crossover (create-folks knapPI_16_20_1000_1)

;(defn hill-climb
;  [winner max-tries max-tries-perhill instance]
;  (loop [num-tries 0
;         current-best winner
;         previous-hill winner]
;    (if (>= num-tries max-tries)
;      (max-key :score current-best previous-hill)
;      (if(=(mod num-tries max-tries-perhill) 0)

        ;(let [tweaked-choices (tweak-choice (:choices current-best)  2)
        ;      new-answer (find-answer tweaked-choices instance)
        ;      scored-new-answer (add-score new-answer)]
        ;  (recur (inc num-tries)
        ;         (add-score (random-answer instance))
        ;         (max-key :score current-best previous-hill)))

 ;       (let [tweaked-choices (tweak-choice (:choices current-best)  2)
   ;           new-answer (find-answer tweaked-choices instance)
   ;           scored-new-answer (add-score new-answer)]
    ;      (recur (inc num-tries)
   ;              (max-key :score current-best scored-new-answer)
  ;               previous-hill))
      ;  ))))

(defn random-flip-check-iterate [instance max-tries currentBest]
    (let [finalAnswer (assoc currentBest :choices (tweak-choice (:choices currentBest) (rand-int 4)))
          finalFinalAnswer (assoc finalAnswer :total-weight (reduce + (map :weight (included-items (:items (:instance currentBest)) (:choices finalAnswer)))))
          finalFinalFinalAnswer (assoc finalFinalAnswer :total-value (reduce + (map :value (included-items (:items (:instance currentBest)) (:choices finalAnswer)))))]

    (if (> (:score (add-score finalFinalFinalAnswer)) (:score (add-score currentBest)))
      (add-score finalFinalFinalAnswer)
      (add-score currentBest)
      )
    )
  )





(defn hill-climber [make-answer instance max-tries]
    (nth (take max-tries (iterate (partial random-flip-check-iterate instance max-tries) (make-answer instance))) (dec max-tries)))


(hill-climber random-answer knapPI_16_20_1000_1 1000)


