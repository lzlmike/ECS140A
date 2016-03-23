/*******************************************/
/**    Your solution goes in this file    **/ 
/*******************************************/
/* Zuolin Li, 998829351     */
/* Jingyang Chen, 998758752 */


year_1953_1996_novels(Name):- 
    novel(Name, Year), (Year is 1953 ;Year is 1996).
    
period_1800_1900_novels(Name):-
    novel(Name, Year), Year >= 1800, Year =< 1900.

lotr_fans(F):-
    fan(F, List), member( the_lord_of_the_rings, List). 

common(A, B):-
    member(X, A), member(X, B), !.
    
author_names(Author):-
    author(Author, List1), fan(chandler, List2), common(List1, List2).
    
fans_names(Name):-
    fan(Name, List1), author(brandon_sanderson, List2), common(List1, List2).

common2(A, B, X):-  
    member(X, A), member(X, B).
    
mutual_novels(Nov):-
    fan(phoebe, List1), fan(ross, List2), fan(monica, List3), 
    (common2(List1, List2, Nov); common2(List1, List3, Nov); common2(List3, List2, Nov)).
    

isMember(X, [X | Tail]).
isMember(X, [Head | Tail]):- isMember(X, Tail).

isUnion([], T, T).
isUnion([X|T1], T2, T3):- isMember(X, T2), !, isUnion(T1, T2, T3).
isUnion([X|T1], T2, [X|T3]):- isUnion(T1, T2, T3).

isIntersection([], T, []).
isIntersection([X|T1], T2, [X|T3]):- isMember(X, T2), !, isIntersection(T1, T2, T3).
isIntersection([X|T1], T2, T3):- isIntersection(T1, T2, T3).

%use select to see if it is a member of another.
isEqual([], []).
isEqual([X|T1], T2):- isMember(X, T2), select(X, T2, T3), isEqual(T1, T3).

%powerset of a set, find out all the subset, then collect them.
powerSet(Set, P) :-
    findall(SubSet, sub(SubSet, Set), P).

/*sub([],[]).
sub(L, [H|T]):-
  append([H|T], _, L).
sub([_|L], P):-
  sub(L, P).*/
  
sub([], []).
sub([], [_|_]).
sub([Ah|A], [Ah|B]):- sub(A, B).
sub([Ah|A], [_|B]):- append(_, [Ah|C], B),
    sub(A, C).
    

%Part3 puzzel
%define opposite, left side and right side of a river.
opposite(left, right).
opposite(right, left).

%define state which has element farmer,wolf,goat, cabbage.
%unsafe when wolf and goat are together, goat and cabbage are togather.
state(_, _, _, _).
unsafe(state(A, B, B, _)):- opposite(A, B).
unsafe(state(A, _, B, B)):- opposite(A, B).
%negation of unsafe.
safe(A):- \+ unsafe(A).
%take one of them from one side to another.
take(X, A, B):- opposite(A, B).

%define the edges/arc, when take from one side to another, the state changes.
arc(take(wolf, Side1, Side2), state(Side1, Side1,G, C), state(Side2, Side2, G, C)):-
opposite(Side1, Side2), safe(state(Side2, Side2, G, C)).
arc(take(goat, Side1, Side2), state(Side1, W, Side1, C), state(Side2, W, Side2, C)):-
opposite(Side1, Side2), safe(state(Side2, W, Side2, C)).
arc(take(cabbage, Side1, Side2), state(Side1, W, G, Side1), state(Side2, W, G, Side2)):-
opposite(Side1, Side2), safe(state(Side2, W, G, Side2)).
arc(take(none, Side1, Side2), state(Side1, W, G, C), state(Side2, W, G, C)):-
opposite(Side1, Side2), safe(state(Side2, W, G, C)).

%print out the path/takes it went through.
print_path([]).
print_path([arc(take(X, A , B), _, _)|T]):-
    print_path(T),
    write('take'), write('('), write(X), write(','), write(A), write(','), 
    write(B), write(')'), nl.
    
%go is succeeds when the start sate is legal, destination is legal,
%and it is not a path that has been visited.
go(A, B, Path):-
    safe(A), Arc=arc(_, A, A1), \+ member(Arc, Path), Arc, go(A1, B, [Arc|Path]).
    
go(A, B, Path):-
    safe(A), Arc=arc(_, A, B), \+ member(Arc, Path), Arc, print_path([Arc|Path]).

solve :- go(state(left, left, left, left), state(right, right, right, right), []), !.







    
    
    
    
    
    
    
    

